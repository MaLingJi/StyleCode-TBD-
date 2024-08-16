package com.outfit_share.controller.posts;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.outfit_share.entity.post.Comment;
import com.outfit_share.entity.users.UserDetail;
import com.outfit_share.service.post.CommentService;
import com.outfit_share.service.users.UserDetailService;


@Controller
public class CommentController {

	@Autowired
	private CommentService comtService;
	
	@Autowired
	private UserDetailService userDService;
	
	@GetMapping("/comment/add")
	public String addComment(Module module) {
		return "comment/addComment";
	}
	
	@PostMapping("/comment/addCommemt")
	public String comment(
							@RequestParam Integer commentId,
							@RequestParam Integer postId,
							@RequestParam Integer userId,
							@RequestParam String comment,
							@RequestParam(required = false) @DateTimeFormat(iso =
							DateTimeFormat.ISO.DATE_TIME) Date createdat,
							@RequestParam(required = false) @DateTimeFormat(iso =
							DateTimeFormat.ISO.DATE_TIME) Date deletedat,
							Model model) {
		
		UserDetail user = userDService.findUserById(userId);
		if(user == null) {
			model.addAttribute("錯誤","未找到使用者");
			return "comment/addComment";
		}
		
		Comment comt = new Comment();
		comt.setUserDetail(user);
		comt.setComment(comment);
		comt.setCreatedAt(createdat);
		comt.setDeletedAt(null);// 尚未刪除
		
		comtService.createComment(comt);
		
		model.addAttribute("OK","新增成功");
		return "comment/addComment";
	}
	
	
}
