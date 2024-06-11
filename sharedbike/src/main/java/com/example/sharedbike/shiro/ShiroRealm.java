package com.example.sharedbike.shiro;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.example.sharedbike.entity.Admin;
import com.example.sharedbike.mapper.AdminMapper;
import com.example.sharedbike.service.AdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 同时开启身份验证和权限验证，需要继承 AuthorizingRealm 
 * 并实现其  doGetAuthenticationInfo()和 doGetAuthorizationInfo 两个方法
 */
@SuppressWarnings("serial")
public class ShiroRealm extends AuthorizingRealm {
	@Autowired
	private AdminMapper adminMapper;
	//public static Map<String, Admin> userMap = new HashMap<String, Admin>(16);
	public static Map<String, Set<String>> roleMap = new HashMap<String, Set<String>>(16);
	public static Map<String, Set<String>> permMap = new HashMap<String, Set<String>>(16);

	/**
	 * 限定这个 Realm 只处理 UsernamePasswordToken
	 */
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken;
	}
	
	/**
	 * 查询数据库，将获取到的用户安全数据封装返回
	 */

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		String username = token.getUsername();
		Admin user = adminMapper.findByUsername(username);
		if (user == null){
			throw new AuthenticationException("账号不存在");
		}
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user,user.getPassword(), ByteSource.Util.bytes(user.getUsername()),getName());
		//return的时候进行校验
		return authenticationInfo;
	}
	/**
	 * 查询数据库，将获取到的用户的角色及权限信息返回
	 */
//	@Override
//	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//		// 获取当前用户
//		Admin currentUser = (Admin) SecurityUtils.getSubject().getPrincipal();
//		// Admin currentUser = (Admin)principals.getPrimaryPrincipal();
//		// 查询数据库，获取用户的角色信息
//		Set<String> roles = roleMap.get(currentUser.getUsername());
//		// 查询数据库，获取用户的权限信息
//		Set<String> perms = permMap.get(currentUser.getUsername());
//
//		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//		info.setRoles(roles);
//		info.setStringPermissions(perms);
//		return info;
//	}
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Admin currentUser = (Admin) principals.getPrimaryPrincipal();
		Set<String> roles = new HashSet<>();
		roles.add(currentUser.getPrivileges().toString()); // 添加用户的角色
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setRoles(roles);

		// 添加用户的权限
		info.addStringPermission(currentUser.getPrivileges().toString());

		return info;
	}


}