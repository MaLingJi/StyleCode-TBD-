package com.outfit_share.controller.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.outfit_share.service.posts.UsersService;

@Controller
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/users/loginPost")
    public String loginPost(
            @RequestParam Integer id, // 使用 Integer 类型的 ID
            @RequestParam String email,
            @RequestParam String passwd,
            Model model) {

        // 验证用户 ID、电子邮件和密码
        boolean isAuthenticated = usersService.authenticateUser(id, email, passwd);

        if (isAuthenticated) {
            model.addAttribute("okMsg", "登入成功");
            return "/posts/add";
        } else {
            model.addAttribute("errorMsg", "登入失敗，請檢查您的 ID、電子郵件和密碼");
            return "login"; // 登录失败返回到登录页面
        }
    }
}
