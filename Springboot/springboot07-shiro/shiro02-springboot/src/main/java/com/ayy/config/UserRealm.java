package com.ayy.config;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 20/03/2021
 * @ Version 1.0
 */

public class UserRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("doGetAuthorizationInfo --- principals");
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("doGetAuthorizationInfo --- token");
        String name = "user1";
        String password = "123456";
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;

        if(!usernamePasswordToken.getUsername().equals(name)){
            return null;
        }

        return new SimpleAuthenticationInfo("",password,"");
    }
}
