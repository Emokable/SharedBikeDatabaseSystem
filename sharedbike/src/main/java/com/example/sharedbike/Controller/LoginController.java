/*
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-14 22:03:08
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-18 03:31:22
 */
package com.example.sharedbike.Controller;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSONObject;
import com.example.sharedbike.domin.BaseResponse;
import com.example.sharedbike.entity.Admin;
import com.example.sharedbike.jwt.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import java.sql.Struct;

@RestController
public class LoginController {

	@PostMapping(value = "/login")
	public Object userLogin(@RequestBody Admin admin, ServletResponse response) {

		// 获取当前用户主体
		Subject subject = SecurityUtils.getSubject();
		String msg = null;
		boolean loginSuccess = false;
		// 将用户名和密码封装成 UsernamePasswordToken 对象
		UsernamePasswordToken token = new UsernamePasswordToken(admin.getUsername(), admin.getPassword());
		try {
			subject.login(token);
			msg = "登录成功。";
			loginSuccess = true;
		} catch (UnknownAccountException uae) { // 账号不存在
			msg = "用户名不存在，请检查后重新输入！";
		} catch (IncorrectCredentialsException ice) { // 账号与密码不匹配
			msg = "用户名与密码不匹配，请检查后重新输入！";
		} catch (LockedAccountException lae) { // 账号已被锁定
			msg = "该账户已被锁定，如需解锁请联系管理员！";
		} catch (AuthenticationException ae) { // 其他身份验证异常
			msg = "登录异常，请联系管理员！";
		}
		BaseResponse<Object> ret = new BaseResponse<Object>();
		if (loginSuccess) {
			// 若登录成功，签发 JWT token
			String jwtToken = JwtUtils.sign(admin.getUsername(), JwtUtils.SECRET);
			// 将签发的 JWT token 设置到 HttpServletResponse 的 Header 中
			((HttpServletResponse) response).setHeader(JwtUtils.AUTH_HEADER, jwtToken);
			//
			// 获取用户的权限信息
			Admin currentAdmin = (Admin) subject.getPrincipal();
			String privileges = currentAdmin.getPrivileges().toString();
			int adminid = currentAdmin.getAdminid();
			String avatar = currentAdmin.getAvatar();
			// 把以上3个信息存入data
			JSONObject data = new JSONObject();
			data.put("privileges", privileges);
			data.put("adminid", adminid);
			data.put("avatar", avatar);
			ret.setData(data);
			ret.setErrCode(0);
			ret.setMsg(msg);

			return ret;
		} else {
			ret.setErrCode(401);
			ret.setMsg(msg);
			return ret;
		}

	}

	@GetMapping("/logout")
	public Object logout() {
		BaseResponse<Object> ret = new BaseResponse<Object>();
		ret.setErrCode(0);
		ret.setMsg("退出登录");
		return ret;
	}
}
