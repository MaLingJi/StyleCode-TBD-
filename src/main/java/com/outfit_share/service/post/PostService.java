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
import com.outfit_share.entity.post.ProductTag;
import com.outfit_share.entity.post.ProductTagDTO;
import com.outfit_share.entity.post.Tags;
import com.outfit_share.repository.post.PostRepository;
import org.springframework.data.domain.PageRequest;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import com.outfit_share.repository.post.PostTagsRepository;
import com.outfit_share.repository.post.ProductTagRepository;
import com.outfit_share.repository.post.TagsRepository;
import com.outfit_share.repository.product.SubcategoryRepository;
import com.outfit_share.repository.users.UserDetailRepository;

@Service
@Transactional
public class PostService {

	@Autowired
	private PostRepository postRepo;

	@Autowired
	private PostTagsRepository postTagsRepository;

	@Autowired
	private TagsRepository tagsRepository;

	@Autowired
	private UserDetailRepository userDetailRepository;

	@Autowired
	private ProductTagRepository productTagRepository;

	@Autowired
	private SubcategoryRepository subcategoryRepository;

	public Post createPost(Post post) {
		post.setCreatedAt(new Date());
		return postRepo.save(post);
	}

	@Transactional
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
		System.out.println("文章標籤: " + postTagsList);
		// 保存所有的 PostTags 關聯
		postTagsRepository.saveAll(postTagsList);

		System.out.println("productTags: " + postDTO.getProductTags());

		// 處理 ProductTag
		List<ProductTag> productTagsList = new ArrayList<>();
		for (ProductTagDTO productTagDTO : postDTO.getProductTags()) {
			ProductTag productTag = new ProductTag();
			productTag.setProductName(productTagDTO.getProductName());
			productTag.setPost(post);
			productTag.setSubcategory(subcategoryRepository.findById(productTagDTO.getSubcategoryId())
					.orElseThrow(() -> new RuntimeException("Subcategory not found")));
			System.out.println("---------------------------------------------");
			System.out.println("productTag: " + productTag);
			System.out.println("---------------------------------------------");
			productTagsList.add(productTag);
		}
		// System.out.println("單品標籤: " + productTagsList);
		productTagRepository.saveAll(productTagsList);

		// 將所有關聯加回 PostDTO 返回
		postDTO.setPostId(post.getPostId());
		postDTO.setCreatedAt(post.getCreatedAt());
		postDTO.setDeletedAt(post.getDeletedAt());

		for (PostTags postTag : postTagsList) {
			postDTO.getPostTags().add(new PostTagsDTO(postTag));
		}

		for (ProductTag productTag : productTagsList) {
			postDTO.getProductTags().add(new ProductTagDTO(productTag));
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

	public List<PostDTO> findAllPost() {
		List<Post> list = postRepo.findAll();
		List<PostDTO> dtoList = new ArrayList<>();
		for (Post post : list) {
			if (post.getImages() == null) {
				post.setImages(new ArrayList<>()); // 初始化為空列表
			}
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
			post.setContentText(newpost.getContentText());
			return postRepo.save(post);
		}
		return null;
	}

	public List<Post> searchPostsByTypeAndKeyword(String contentType, String keyword) {
		return postRepo.findPostByTypeAndKeyword(contentType, keyword);
	}

	public List<PostDTO> findLatestPosts(int limit) {
		PageRequest pageRequest = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
		List<Post> latestPosts = postRepo.findAll(pageRequest).getContent();
		return latestPosts.stream()
				.map(post -> {
					PostDTO dto = new PostDTO(post);
					dto.setImageUrls(post.getImages().stream()
							.map(image -> image.getImgUrl())
							.collect(Collectors.toList()));
					return dto;
				})
				.collect(Collectors.toList());
	}
}
