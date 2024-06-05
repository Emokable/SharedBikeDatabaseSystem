package com.example.sharedbike.Config;

import com.example.sharedbike.entity.Privileges;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.example.sharedbike.entity.Admin;
import com.example.sharedbike.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private AdminService adminService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        String password = new String(usernamePasswordToken.getPassword());

        Admin user = adminService.authenticate(username, password);
        if (user == null) {
            throw new UnknownAccountException("账号或密码不正确");
        }

        return new SimpleAuthenticationInfo(user, password, getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Admin admin = (Admin) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Privileges privileges = admin.getPrivileges();

        if (privileges != null) {
            switch (privileges) {
                case read_only:
                    authorizationInfo.addRole("read_only");
                    authorizationInfo.addStringPermission("read");
                    break;
                case data_modification:
                    authorizationInfo.addRole("data_modification");
                    authorizationInfo.addStringPermission("read");
                    authorizationInfo.addStringPermission("write");
                    break;
                case superuser:
                    authorizationInfo.addRole("superuser");
                    authorizationInfo.addStringPermission("read");
                    authorizationInfo.addStringPermission("write");
                    authorizationInfo.addStringPermission("manage");
                    break;
                default:
                    break;
            }
        }

        return authorizationInfo;
    }
}

