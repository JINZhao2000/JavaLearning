package com.ayy.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 18/03/2021
 * @ Version 1.0
 */

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/level/1/**").hasRole("vip1")
                .antMatchers("/level/2/**").hasRole("vip2")
                .antMatchers("/level/3/**").hasRole("vip3");
        http.formLogin().loginPage("/toLogin").loginProcessingUrl("/login").usernameParameter("uname").passwordParameter("pwd");
        http.csrf().disable();
        http.rememberMe().rememberMeParameter("rememberme");
        http.logout().logoutSuccessUrl("/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        auth.inMemoryAuthentication().passwordEncoder(encoder)
                .withUser("user1").password(encoder.encode("123456")).roles("vip1").and()
                .withUser("user2").password(encoder.encode("123456")).roles("vip1","vip2").and()
                .withUser("root").password(encoder.encode("root")).roles("vip1","vip2","vip3");
    }
}
