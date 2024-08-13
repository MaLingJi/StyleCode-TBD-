package com.outfit_share.service.post;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.outfit_share.entity.post.Posts;
import com.outfit_share.repository.post.PostsRepository;

@Service
public class PostsService {

	@Autowired
	private PostsRepository postsRepo;
	
	public Posts savePosts(Posts posts) {
		return postsRepo.save(posts);
	}
	
	public Posts findPostsById(Integer id) {
		Optional<Posts> optional = postsRepo.findById(id);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	public void deletePostsById(Integer id) {
		postsRepo.deleteById(id);
	}
	
	public List<Posts> findAllPosts(){
		return postsRepo.findAll();
	}
	
	@Transactional
	public Posts updatePosts(Integer id,String newPosts) {
		Optional<Posts> optional = postsRepo.findById(id);
		
		if(optional.isPresent()) {
			Posts posts = optional.get();
			posts.setContentText(newPosts);//內容
			posts.setContentType(newPosts);//文章型態
			posts.setPostTitle(newPosts);//標題
			return posts;
		}
		return null;
	}
}
