package com.outfit_share.service.post;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.outfit_share.entity.post.Collections;
import com.outfit_share.entity.post.CollectionsId;
import com.outfit_share.entity.post.Likes;
import com.outfit_share.entity.post.LikesId;
import com.outfit_share.entity.post.Post;
import com.outfit_share.entity.users.UserDetail;
import com.outfit_share.repository.post.CollectionsRepository;
import com.outfit_share.repository.post.PostRepository;
import com.outfit_share.repository.users.UserDetailRepository;

@Service
public class CollectionsService {

	@Autowired
	private CollectionsRepository collectsRepo;
	
	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private UserDetailRepository userDetailRepo;
	
	public Collections createcollections(Collections collects) {
		Post post = postRepo.findById(collects.getPosts().getPostId()).orElse(null);
		UserDetail userDetail = userDetailRepo.findById(collects.getUserDetail().getId()).orElse(null);
	
		if (post == null) {
			throw new IllegalArgumentException("找不到貼文");
		}
		if (userDetail == null) {
			throw new IllegalArgumentException("未找到用戶詳細信息");
		}
		collects.setPosts(post);
		collects.setUserDetail(userDetail);
		
		return collectsRepo.save(collects);
	}
	//複合主鍵類型不是單個 Integer
	//尋找postId和userId 根據你的實際鍵值構建CollectionsId對象進行 找Id
	public Collections findCollectionsbyId(Integer postId, Integer userId) {
		CollectionsId collectionsId = new CollectionsId(postId,userId);
		Optional<Collections> optional = collectsRepo.findById(collectionsId);
		
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	//複合主鍵類型不是單個 Integer
	//尋找postId和userId 根據你的實際鍵值構建CollectionsId對象進行 刪除
	public void deletecollectsById(Integer postId, Integer userId) {
		CollectionsId collectionsId = new CollectionsId(postId, userId);
		collectsRepo.deleteById(collectionsId);
	}
	
	public boolean toggleLikes(Integer postId,Integer userId) {
		CollectionsId collectionsId = new CollectionsId(postId,userId);
	    Optional<Collections> existingCollections = collectsRepo.findById(collectionsId);
	    
	    if (existingCollections.isPresent()) {
	        // 如果找到收藏紀錄，則刪除收藏
	    	collectsRepo.delete(existingCollections.get());
	        return false;
	    } else {
	        // 如果未找到收藏紀錄，則新增收藏
	        Post post = postRepo.findById(postId).orElseThrow(() -> new RuntimeException("Post找不到"));
	        UserDetail userDetail = userDetailRepo.findById(userId).orElseThrow(() -> new RuntimeException("User找不到"));
	        
	        Collections collections = new Collections();
	        collections.setCollectionsId(collectionsId);
	        collections.setPosts(post);
	        collections.setUserDetail(userDetail);
	        
	        collectsRepo.save(collections);
	        return true; // 表示新增了收藏
	    }
	}
}
