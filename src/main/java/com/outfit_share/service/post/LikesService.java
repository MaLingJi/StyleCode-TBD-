package com.outfit_share.service.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	// 複合主鍵類型不是單個 Integer
	// 尋找postId和userId 根據你的實際鍵值構建LikesId對象進行 找Id
	public Likes findLikesById(Integer postId, Integer userId) {
		LikesId likesId = new LikesId(postId, userId);
		return likesRepo.findById(likesId).orElse(null);
	}

	// 複合主鍵類型不是單個 Integer
	// 尋找postId和userId 根據你的實際鍵值構建LikesId對象進行 刪除
	public void deleteLikesById(Integer postId, Integer userId) {
		LikesId likesId = new LikesId(postId, userId);
		likesRepo.deleteById(likesId);
	}

	public Likes save(Likes like) {
		return likesRepo.save(like);
	}

	@Transactional
    public void toggleLike(Integer postId, Integer userId) {
        // 創建 LikesId 對象
        LikesId likesId = new LikesId(postId, userId);

        // 檢查是否存在
        boolean exists = likesRepo.existsByLikesId(likesId);

        if (exists) {
            // 如果已經點讚，則取消點讚
        	likesRepo.deleteById(likesId);
        } else {
            // 如果尚未點讚，則添加點讚

            // 根據 postId 獲取 Post 實例
            Post post = postRepo.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("無效的 postId: " + postId));

            // 根據 userId 獲取 UserDetail 實例
            UserDetail userDetail = userDetailRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("無效的 userId: " + userId));

            // 創建 Likes 實例
            Likes like = new Likes();
            like.setLikesId(likesId);
            like.setPost(post);
            like.setUserDetail(userDetail);

            // 保存 Likes 實例
            likesRepo.save(like);
	        }
	    }
}
