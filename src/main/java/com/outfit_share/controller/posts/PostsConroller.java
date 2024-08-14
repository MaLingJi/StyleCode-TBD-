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
import com.outfit_share.service.post.PostService;
import com.outfit_share.service.users.UsersService;

@Controller
public class PostsConroller {

	// @Autowired
	// private PostsService postsService;

	// @Autowired
	// private UsersService usersService;

	// @GetMapping("/posts/add")
	// public String addPosts(Model model) {
	// // Integer currentUserId = getCurrentUserId();//實現此方法以獲取當前用戶 ID
	// // model.addAttribute("currentUserId", currentUserId);
	// return "posts/addPosts";
	// }

	// @PostMapping("/posts/addPosts")
	// public String posts(
	// @RequestParam Integer userId,
	// @RequestParam String contenttype,
	// @RequestParam String posttitle,
	// @RequestParam String contenttext,
	// @RequestParam(required = false) @DateTimeFormat(iso =
	// DateTimeFormat.ISO.DATE_TIME) Date createdat,
	// @RequestParam(required = false) @DateTimeFormat(iso =
	// DateTimeFormat.ISO.DATE_TIME) Date deletedat,
	// Model model) {

	// // Integer userIdInt = Integer.valueOf(userId); // 轉換為 Integer
	// // System.out.println("userId: " + userId);
	// Users user = usersService.findUsersById(userId);
	// if (userId == null) {
	// model.addAttribute("錯誤", "未找到用戶");
	// return "posts/addPosts";
	// }

	// Posts posts = new Posts();
	// posts.setUsers(user);
	// posts.setContentType(contenttype);
	// posts.setContentText(contenttext);
	// posts.setPostTitle(posttitle);
	// posts.setCreatedAt(createdat);
	// posts.setDeletedAt(null);// 尚未刪除

	// postsService.createPosts(posts);

	// model.addAttribute("OK", "新增成功");
	// return "posts/addPosts";
	// }

	// @GetMapping("/posts/list")
	// public String findAll(Model model) {
	// List<Posts> list = postsService.findAllPosts();

	// model.addAttribute("posts", list);
	// return "posts/showPosts";
	// }

	// @GetMapping("/posts/update")
	// public String updatePosts(@RequestParam Integer id, Model model) {
	// Posts posts = postsService.findPostsById(id);
	// if (posts == null) {
	// model.addAttribute("錯誤", "未找到貼文");
	// return "posts/list";
	// }
	// model.addAttribute("posts", posts);
	// return "posts/upPosts";
	// }

	// @PostMapping("/posts/updatePoasts")
	// public String updatePoastspage(@ModelAttribute Posts posts, Model model) {
	// Posts existingPost = postsService.findPostsById(posts.getPostId());

	// if (existingPost != null) {
	// model.addAttribute("錯誤", "未找到貼文");
	// return "posts/list";
	// }
	// // 更新貼文資料
	// existingPost.setContentText(posts.getContentText());
	// existingPost.setContentType(posts.getContentType());
	// existingPost.setPostTitle(posts.getPostTitle());
	// existingPost.setDeletedAt(posts.getDeletedAt()); // 可選：設定刪除時間

	// postsService.createPosts(existingPost);

	// return "redirect:/posts/list";
	// }

	// @PostMapping("/posts/delete")
	// public String deletePost(@RequestParam Integer id, Model model) {
	// Posts post = postsService.findPostsById(id);
	// if (post == null) {
	// model.addAttribute("錯誤", "未找到貼文");
	// return "posts/list";
	// }

	// postsService.deletePostsById(id);

	// return "redirect:/posts/list";
	// }
}
