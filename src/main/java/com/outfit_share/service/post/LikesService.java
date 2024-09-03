package com.outfit_share.service.post;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.outfit_share.entity.post.Likes;
import com.outfit_share.entity.post.LikesId;
import com.outfit_share.entity.post.Post;
import com.outfit_share.entity.users.UserDetail;
import com.outfit_share.repository.post.LikesRepository;
import com.outfit_share.repository.post.PostRepository;
import com.outfit_share.repository.users.UserDetailRepository;

@Service
public class LikesService {

	@Autowired
	private LikesRepository likesRepo;
	
	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private UserDetailRepository userDetailRepo;
	
	public Likes createLikes(Likes likes) {
		Post post = postRepo.findById(likes.getPost().getPostId()).orElse(null);
		UserDetail userDetail = userDetailRepo.findById(likes.getUserDetail().getId()).orElse(null);
		
		if (post == null) {
			throw new IllegalArgumentException("找不到貼文");
		}
		if (userDetail == null) {
			throw new IllegalArgumentException("未找到用戶詳細信息");
		}
		
		likes.setPost(post);
		likes.setUserDetail(userDetail);
		
		return likesRepo.save(likes);
	}
	//複合主鍵類型不是單個 Integer
	//尋找postId和userId 根據你的實際鍵值構建LikesId對象進行 找Id
	public Likes findLikesById(Integer postId, Integer userId) {
		LikesId likesId = new LikesId(postId,userId);
		Optional<Likes> optional = likesRepo.findById(likesId);
		
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	//複合主鍵類型不是單個 Integer
	//尋找postId和userId 根據你的實際鍵值構建LikesId對象進行 刪除
	public void deleteLikesById(Integer postId, Integer userId) {
		LikesId likesId = new LikesId(postId, userId);
		likesRepo.deleteById(likesId);
	}
	
}
