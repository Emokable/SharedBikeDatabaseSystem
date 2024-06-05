package com.example.sharedbike.Controller;

import com.example.sharedbike.result.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import com.example.sharedbike.entity.Admin;

import java.util.Objects;

@Controller
public class LoginController {

    @CrossOrigin
    @PostMapping(value = "api/login")
    @ResponseBody
    public Result login(@RequestBody Admin requestUser) {
        // 对 html 标签进行转义，防止 XSS 攻击
        String username = requestUser.getUsername();
//        username = HtmlUtils.htmlEscape(username);
        System.out.println(username);
        System.out.println(requestUser.getPassword());
        if (!username.equals("admin") || !requestUser.getPassword().equals("123456")){
            String message = "账号密码错误";
            System.out.println("test");
            return new Result(400);
        } else {
            return new Result(200);
        }
    }
}

