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

	 @GetMapping("/posts/list")
	 public String findAll(Model model) {
	 List<Post> list = postService.findAllPost();

	 model.addAttribute("posts", list);
	 return "posts/showPosts";
	 }

	 @GetMapping("/posts/update")
	 public String updatePosts(@RequestParam Integer id, Model model) {
	 Post post = postService.findPostById(id);
	 if (post == null) {
	 model.addAttribute("錯誤", "未找到貼文");
	 return "posts/list";
	 }
	 model.addAttribute("posts", post);
	 return "posts/upPosts";
	 }

	 @PostMapping("/posts/updatePoasts")
	 public String updatePoastspage(@ModelAttribute Post post, Model model) {
	 Post existingPost = postService.findPostById(post.getPostId());

	 if (existingPost != null) {
	 model.addAttribute("錯誤", "未找到貼文");
	 return "posts/list";
	 }
	 // 更新貼文資料
	 existingPost.setContentText(post.getContentText());
	 existingPost.setContentType(post.getContentType());
	 existingPost.setPostTitle(post.getPostTitle());
	 existingPost.setDeletedAt(post.getDeletedAt()); // 可選：設定刪除時間

	 postService.createPost(existingPost);

	 return "redirect:/posts/list";
	 }

	 @PostMapping("/posts/delete")
	 public String deletePost(@RequestParam Integer id, Model model) {
	 Post post = postService.findPostById(id);
	 if (post == null) {
	 model.addAttribute("錯誤", "未找到貼文");
	 return "posts/list";
	 }

	 postService.deletePostById(id);

	 return "redirect:/posts/list";
	 }
}
