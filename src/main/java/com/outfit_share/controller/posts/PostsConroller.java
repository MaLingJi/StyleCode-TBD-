package com.outfit_share.controller.posts;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.outfit_share.entity.post.Post;
import com.outfit_share.entity.users.UserDetail;
import com.outfit_share.service.post.PostService;
import com.outfit_share.service.users.UserDetailService;

@Controller
public class PostsConroller {

	 @Autowired
	 private PostService postService;

	 @Autowired
	 private UserDetailService userDService;

	 @GetMapping("/posts/add")
	 public String addPosts(Model model) {
//	  Integer currentUserId = getCurrentUserId();//實現此方法以獲取當前用戶 ID
//	  model.addAttribute("currentUserId", currentUserId);
	 return "posts/addPosts";
	 }

	 @PostMapping("/posts/addPosts")
	 public String posts(
	 @RequestParam Integer userId,
	 @RequestParam String contenttype,
	 @RequestParam String posttitle,
	 @RequestParam String contenttext,
	 @RequestParam(required = false) @DateTimeFormat(iso =
	 DateTimeFormat.ISO.DATE_TIME) Date createdat,
	 @RequestParam(required = false) @DateTimeFormat(iso =
	 DateTimeFormat.ISO.DATE_TIME) Date deletedat,
	 Model model) {

	 // Integer userIdInt = Integer.valueOf(userId); // 轉換為 Integer
	 // System.out.println("userId: " + userId);
	 UserDetail user = userDService.findUserById(userId);
	 if (user  == null) {
	 model.addAttribute("錯誤", "未找到用戶");
	 return "posts/addPosts";
	 }

	 Post post = new Post();
	 post.setUserDetail(user);
	 post.setContentType(contenttype);
	 post.setContentText(contenttext);
	 post.setPostTitle(posttitle);
	 post.setCreatedAt(createdat);
	 post.setDeletedAt(null);// 尚未刪除

	 postService.createPost(post);
	 
	 model.addAttribute("OK", "新增成功");
	 return "posts/addPosts";
	 }
	 
	 //顯示新增貼文表單(後端可能會用到)
//	 @GetMapping("/new/posts")
//	 public String showNewposts(Model model) {
//		 model.addAttribute("post",new Post());
//		 return "posts/newPosts";
//	 }

	 @GetMapping("/posts/list")
	 public String findAll(Model model) {
	 List<Post> posts = postService.findAllPost();
	 model.addAttribute("posts", posts);

	 return "posts/showPosts";
	 }

	 @GetMapping("/posts/update")
	 public String updatePosts(@RequestParam Integer userId, Model model) {
	 Post post = postService.findPostById(userId);
	 if (post == null) {
	 model.addAttribute("錯誤", "未找到貼文");
//	 model.addAttribute("userDetail", post.getUserDetail());
	 return "posts/list";
	 }
	 model.addAttribute("posts", post);
	 return "posts/upPosts";
	 }

	 @PostMapping("/posts/updatePost")
	 public String updatePosts(@ModelAttribute Post post, Model model) {
	 Post existingPost = postService.findPostById(post.getPostId());
	 
	 if (existingPost == null) {
	 model.addAttribute("錯誤", "未找到貼文");
	 return "posts/list";
	 }
	 // 更新貼文資料
	 existingPost.setContentText(post.getContentText());
	 existingPost.setPostTitle(post.getPostTitle());
//	 existingPost.setContentType(post.getContentType());//能自改討論 分享?

	 postService.createPost(existingPost);

	 return "redirect:/posts/list";
	 }

	 // 假刪除貼文
	 @PostMapping("/posts/delete")
	 public String deletePost(@RequestParam Integer postId) {
		 postService.deletePostById(postId);

	 return "redirect:/posts/list";
	 }
}
