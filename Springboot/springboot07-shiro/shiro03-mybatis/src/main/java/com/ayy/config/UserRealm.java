package com.ayy.config;

import com.ayy.bean.User;
import com.ayy.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 20/03/2021
 * @ Version 1.0
 */

public class UserRealm extends AuthorizingRealm {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        User current = (User) subject.getPrincipal();
        // add perms into table and bean of user
        // info.addStringPermission(current.getAuth());
        if(current.getUname().equals("root")){
            info.addStringPermission("user:add");

        }        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        User user = userService.queryUserByName(usernamePasswordToken.getUsername());
        if(null==user){
            return null;
        }
        Subject subject = SecurityUtils.getSubject();
        subject.getSession().setAttribute("currUser",user);
        return new SimpleAuthenticationInfo(user,user.getPwd(),"");
    }
}
