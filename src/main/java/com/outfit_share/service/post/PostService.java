package com.outfit_share.service.post;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.outfit_share.entity.post.Post;
import com.outfit_share.repository.post.PostRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepo;
	
	public Post createPost(Post post) {
		return postRepo.save(post);
	}
	
	public Post findPostById(Integer id) {
		Optional<Post> optional = postRepo.findById(id);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	public void deletePostById(Integer id) {
		postRepo.deleteById(id);
	}
	
	public List<Post> findAllPost(){
		return postRepo.findAll();
	}
	
	@Transactional
	public Post updatePost(Integer id,String newPost) {
		Optional<Post> optional = postRepo.findById(id);
		
		if(optional.isPresent()) {
			Post post = optional.get();
			post.setContentText(newPost);//內容
			post.setContentType(newPost);//文章型態
			post.setPostTitle(newPost);//標題
			return post;
		}
		return null;
	}
}
