package com.outfit_share.service.post;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.outfit_share.entity.post.Post;
import com.outfit_share.entity.post.PostDTO;
import com.outfit_share.entity.post.PostTags;
import com.outfit_share.entity.post.PostTagsDTO;
import com.outfit_share.entity.post.PostTagsId;
import com.outfit_share.entity.post.Tags;
import com.outfit_share.repository.post.PostRepository;
import org.springframework.data.domain.PageRequest;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import com.outfit_share.repository.post.PostTagsRepository;
import com.outfit_share.repository.post.TagsRepository;
import com.outfit_share.repository.users.UserDetailRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepo;

	@Autowired
	private PostTagsRepository postTagsRepository;

	@Autowired
	private TagsRepository tagsRepository;

	@Autowired
	private UserDetailRepository userDetailRepository;

	public Post createPost(Post post) {
		post.setCreatedAt(new Date());
		return postRepo.save(post);
	}

	public PostDTO createPostWithTags(PostDTO postDTO, List<String> tagNames) {
		// 創建 Post 實體
		Post post = new Post();
		post.setContentType(postDTO.getContentType());
		post.setPostTitle(postDTO.getPostTitle());
		post.setContentText(postDTO.getContentText());
		post.setShareId(postDTO.getShareId());
		post.setUserDetail(userDetailRepository.findById(postDTO.getUserId())
				.orElseThrow(() -> new RuntimeException("User not found")));

		// 保存 Post 實體到資料庫
		post = postRepo.save(post);

		// 處理標籤
		List<PostTags> postTagsList = new ArrayList<>();
		for (String tagName : tagNames) {
			// 查詢標籤是否已存在
			Tags tag = tagsRepository.findByName(tagName)
					.orElseGet(() -> {
						// 如果標籤不存在，則創建一個新標籤
						Tags newTag = new Tags();
						newTag.setName(tagName);
						return tagsRepository.save(newTag);
					});

			// 創建 PostTagsId
			PostTagsId postTagsId = new PostTagsId(post.getPostId(), tag.getId());

			// 創建 PostTags 關聯
			PostTags postTag = new PostTags();
			postTag.setPostTagsId(postTagsId);
			postTag.setPost(post);
			postTag.setTags(tag);

			postTagsList.add(postTag);
		}

		// 保存所有的 PostTags 關聯
		postTagsRepository.saveAll(postTagsList);

		// 將所有關聯加回 PostDTO 返回
		postDTO.setPostId(post.getPostId());
		postDTO.setCreatedAt(post.getCreatedAt());
		postDTO.setDeletedAt(post.getDeletedAt());

		for (PostTags postTag : postTagsList) {
			postDTO.getPostTags().add(new PostTagsDTO(postTag));
		}

		return postDTO;
	}

	public PostDTO findPostById(Integer postId) {
		Optional<Post> optional = postRepo.findById(postId);

		if (optional.isPresent()) {
			return new PostDTO(optional.get());
		}
		return null;
	}

	// 軟刪除 設置當前時間
	public Post deletePostById(Integer postId) {
		Optional<Post> deoptional = postRepo.findById(postId);
		if (deoptional.isPresent()) {
			Post post = deoptional.get();
			post.setDeletedAt(new Date());
			return postRepo.save(post);
		}
		return null;
	}

	// public List<Post> findAllPost(){
	// return postRepo.findAll();
	// }

	public List<PostDTO> findAllPost() {
		List<Post> list = postRepo.findAll();
		List<PostDTO> dtoList = new ArrayList<>();
		for (Post post : list) {
			if (post.getImages() == null) {
				post.setImages(new ArrayList<>()); // 初始化為空列表
			}
			// Hibernate.initialize(post.getUserDetail());
			PostDTO postDTO = new PostDTO(post);
			dtoList.add(postDTO);
		}
		return dtoList;
	}

	@Transactional
	public Post updatePost(Integer postId, Post newpost) {
		Optional<Post> upoptional = postRepo.findById(postId);

		if (upoptional.isPresent()) {
			Post post = upoptional.get();
			post.setContentText(newpost.getContentText());// 內容
			// post.setPostTitle(newpost.getPostTitle());//標題是否可變更?
			// post.setContentType(newpost.getContentType());//文章型態(分享.討論)可切換變更?
			return postRepo.save(post);
		}
		return null;
	}

	// 封裝查詢邏輯
	public List<Post> searchPostsByTypeAndKeyword(String contentType, String keyword) {
		return postRepo.findPostByTypeAndKeyword(contentType, keyword);
	}
	
	//首頁輪播圖 前9篇文章的照片
	public List<PostDTO> findLatestPosts(int limit) {
	    PageRequest pageRequest = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
	    List<Post> latestPosts = postRepo.findAll(pageRequest).getContent();
	    return latestPosts.stream()
	        .map(post -> {
	            PostDTO dto = new PostDTO(post);
	            // 假設 post 有一個 getImages() 方法返回圖片列表
	            dto.setImageUrls(post.getImages().stream()
	                .map(image -> image.getImgUrl())
	                .collect(Collectors.toList()));
	            return dto;
	        })
	        .collect(Collectors.toList());
	}
	//用戶ID 查詢該用戶的所有文章
	public List<PostDTO> findPostsByUserId(Integer userId) {
	    return postRepo.findByUserDetail_Id(userId)
	                         .stream()
	                         .map(post -> new PostDTO(post))
	                         .collect(Collectors.toList());
	}
}
