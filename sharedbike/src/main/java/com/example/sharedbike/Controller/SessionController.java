package com.example.sharedbike.Controller;

import com.example.sharedbike.entity.Admin;
import com.example.sharedbike.service.AdminService;
import com.example.sharedbike.result.LoginException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public String login(@RequestBody Admin user) throws LoginException {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());

        try {
            subject.login(token);
            return "登录成功！";
        } catch (Exception e) {
            throw new LoginException("login fail.");
        }
    }

    @GetMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "logout success";
    }
}
