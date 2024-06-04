package com.example.sharedbike.Controller;

import com.example.sharedbike.entity.Admin;
import com.example.sharedbike.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class SessionController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public String login(@RequestBody Admin user, HttpSession session) {
        Admin authenticatedUser = adminService.authenticate(user.getUsername(), user.getPassword());
        if (authenticatedUser != null) {
            session.setAttribute("user", authenticatedUser);
            return "{\"message\": \"登录成功\", \"redirect\": \"/swagger-ui.html\"}";
        }
        return "{\"message\": \"账号或密码错误\"}";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 注销 删除Session
        session.removeAttribute("user");
        return "logout success";
    }
}
