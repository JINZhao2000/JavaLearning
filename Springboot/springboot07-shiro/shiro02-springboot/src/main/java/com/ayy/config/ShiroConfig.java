package com.ayy.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 20/03/2021
 * @ Version 1.0
 */
@Configuration
public class ShiroConfig {
    @Bean(name = "userRealm")
    public UserRealm userRealm(){
        return new UserRealm();
    }

    @Bean(name = "defaultWebSecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        /* anon
           authc
           user
           perms
           role
        */
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/user/*","authc");

        bean.setFilterChainDefinitionMap(filterMap);
        bean.setLoginUrl("/toLogin");
        bean.setSecurityManager(securityManager);
        return bean;
    }
}
