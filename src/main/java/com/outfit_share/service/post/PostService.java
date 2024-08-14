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
	private PostRepository postsRepo;
	
	public Post createPosts(Post posts) {
		return postsRepo.save(posts);
	}
	
	public Post findPostsById(Integer id) {
		Optional<Post> optional = postsRepo.findById(id);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	public void deletePostsById(Integer id) {
		postsRepo.deleteById(id);
	}
	
	public List<Post> findAllPosts(){
		return postsRepo.findAll();
	}
	
	@Transactional
	public Post updatePosts(Integer id,String newPosts) {
		Optional<Post> optional = postsRepo.findById(id);
		
		if(optional.isPresent()) {
			Post posts = optional.get();
			posts.setContentText(newPosts);//內容
			posts.setContentType(newPosts);//文章型態
			posts.setPostTitle(newPosts);//標題
			return posts;
		}
		return null;
	}
}
