package com.outfit_share.service.post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
	public PostDTO createPostWithTags(PostDTO postDTO) {
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

		// 處理 PostTags
		List<PostTags> postTagsList = new ArrayList<>();
		for (PostTagsDTO postTagsDTO : postDTO.getPostTags()) {
			Tags tag = tagsRepository.findByName(postTagsDTO.getTagName()).orElseGet(() -> {
				Tags newTag = new Tags();
				newTag.setName(postTagsDTO.getTagName());
				return tagsRepository.save(newTag);
			});

			PostTagsId postTagsId = new PostTagsId(post.getPostId(), tag.getId());

			PostTags postTag = new PostTags();
			postTag.setPostTagsId(postTagsId);
			postTag.setPost(post);
			postTag.setTags(tag);

			postTagsList.add(postTag);
		}
		postTagsRepository.saveAll(postTagsList);

		// 處理 ProductTag
		List<ProductTag> productTagsList = new ArrayList<>();
		for (ProductTagDTO productTagDTO : postDTO.getProductTags()) {
			ProductTag productTag = new ProductTag();
			productTag.setProductName(productTagDTO.getProductName());
			productTag.setPost(post);
			productTag.setSubcategory(subcategoryRepository.findById(productTagDTO.getSubcategoryId())
					.orElseThrow(() -> new RuntimeException("Subcategory not found")));
			productTagsList.add(productTag);
		}
		productTagRepository.saveAll(productTagsList);

		// 將所有關聯加回 PostDTO 返回
		postDTO.setPostId(post.getPostId());
		postDTO.setCreatedAt(post.getCreatedAt());
		postDTO.setDeletedAt(post.getDeletedAt());

		postDTO.getPostTags().clear();
		for (PostTags postTag : postTagsList) {
			postDTO.getPostTags().add(new PostTagsDTO(postTag));
		}

		postDTO.getProductTags().clear();
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
	public PostDTO updatePostWithTags(Integer postId, PostDTO updatedPostDTO) {
		// 查找現有的文章
		Post post = postRepo.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

		// 更新文章內容
		post.setContentType(updatedPostDTO.getContentType());
		post.setPostTitle(updatedPostDTO.getPostTitle());
		post.setContentText(updatedPostDTO.getContentText());
		post.setShareId(updatedPostDTO.getShareId());

		// 保存更新後的文章
		post = postRepo.save(post);

		// 處理標籤更新
		List<PostTagsDTO> updatedPostTagsDTOs = updatedPostDTO.getPostTags();
		List<PostTags> postTagsList = new ArrayList<>(); // 將變數定義移到外部

		if (updatedPostTagsDTOs != null) {
			// 刪除舊的標籤
			postTagsRepository.deleteByPost(post);

			// 創建並保存新的標籤
			for (PostTagsDTO postTagsDTO : updatedPostTagsDTOs) {
				Tags tag = tagsRepository.findByName(postTagsDTO.getTagName()).orElseGet(() -> {
					Tags newTag = new Tags();
					newTag.setName(postTagsDTO.getTagName());
					return tagsRepository.save(newTag);
				});

				PostTagsId postTagsId = new PostTagsId(post.getPostId(), tag.getId());

				PostTags postTag = new PostTags();
				postTag.setPostTagsId(postTagsId);
				postTag.setPost(post);
				postTag.setTags(tag);

				postTagsList.add(postTag);
			}
			postTagsRepository.saveAll(postTagsList);
		}

		// 處理 ProductTag 更新
		// 刪除舊的 ProductTag
		productTagRepository.deleteByPost(post);

		// 創建並保存新的 ProductTag
		List<ProductTag> productTagsList = new ArrayList<>();
		for (ProductTagDTO productTagDTO : updatedPostDTO.getProductTags()) {
			ProductTag productTag = new ProductTag();
			productTag.setProductName(productTagDTO.getProductName());
			productTag.setPost(post);
			productTag.setSubcategory(subcategoryRepository.findById(productTagDTO.getSubcategoryId())
					.orElseThrow(() -> new RuntimeException("Subcategory not found")));

			productTagsList.add(productTag);
		}
		productTagRepository.saveAll(productTagsList);

		// 更新後的 PostDTO 返回
		updatedPostDTO.setPostId(post.getPostId());
		updatedPostDTO.setCreatedAt(post.getCreatedAt());
		updatedPostDTO.setDeletedAt(post.getDeletedAt());

		updatedPostDTO.getPostTags().clear();
		for (PostTags postTag : postTagsList) {
			updatedPostDTO.getPostTags().add(new PostTagsDTO(postTag));
		}

		updatedPostDTO.getProductTags().clear();
		for (ProductTag productTag : productTagsList) {
			updatedPostDTO.getProductTags().add(new ProductTagDTO(productTag));
		}

		return updatedPostDTO;
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


	// 用戶ID 查詢該用戶的所有文章
	public List<PostDTO> findPostsByUserId(Integer userId) {
		return postRepo.findByUserDetail_Id(userId).stream().map(post -> new PostDTO(post))
				.collect(Collectors.toList());
	}

	//只取分享區的 資料
	public List<PostDTO> findLatestSharePosts(int limit) {
		PageRequest pageRequest = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
		List<Post> latestPosts = postRepo.findLatestPostsByContentType("share", pageRequest);
		return latestPosts.stream().map(post -> {
			PostDTO dto = new PostDTO(post);
			dto.setImageUrls(post.getImages().stream().map(image -> image.getImgUrl()).collect(Collectors.toList()));
			return dto;
		}).collect(Collectors.toList());
	}

	//只取前9筆資料
	public List<PostDTO> findMostLikedPosts(int limit) {
		PageRequest pageRequest = PageRequest.of(0, limit);
		List<Post> mostLikedPosts = postRepo.findMostLikedPosts(pageRequest);
		return mostLikedPosts.stream().map(post -> {
			PostDTO dto = new PostDTO(post);
			dto.setImageUrls(post.getImages().stream().map(image -> image.getImgUrl()).collect(Collectors.toList()));
			return dto;
		}).collect(Collectors.toList());
	}

}
