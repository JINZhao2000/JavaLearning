package com.ayy;
/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.env.BasicIniEnvironment;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple Quickstart application showing how to use Shiro's API.
 *
 * @since 0.9 RC2
 */
public class Quickstart {
    private static final transient Logger log = LoggerFactory.getLogger(Quickstart.class);

    public static void main(String[] args) {
        SecurityManager securityManager = new BasicIniEnvironment("classpath:shiro.ini").getSecurityManager();
        SecurityUtils.setSecurityManager(securityManager);

        // 1. Subject
        Subject currentUser = SecurityUtils.getSubject();

        // 2. Session
        Session session = currentUser.getSession();
        session.setAttribute("key", "value");
        String value = (String) session.getAttribute("key");
        if (value.equals("value")) {
            log.info("Retrieved the correct value! [" + value + "]");
        }

        // - Authentication
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken("username", "password");
            token.setRememberMe(true);
            try {
                currentUser.login(token);
                log.info("User [" + currentUser.getPrincipal() + "] logged in successfully.");
            } catch (UnknownAccountException uae) {
                log.info("There is no user with username of " + token.getPrincipal());
            } catch (IncorrectCredentialsException ice) {
                log.info("Password for account " + token.getPrincipal() + " was incorrect!");
            } catch (LockedAccountException lae) {
                log.info("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.");
            } catch (AuthenticationException ae) {
                //unexpected condition?  error?
            }
        }

        //test a role:
        if (currentUser.hasRole("myrole")) {
            log.info("May the myrole be with you!");
        } else {
            log.info("Hello, mere mortal.");
        }

        // simple
        if (currentUser.isPermitted("mypermission")) {
            log.info("You may use a lightsaber ring.  Use it wisely.");
        } else {
            log.info("Sorry, lightsaber rings are for mypermission masters only.");
        }

        // deep
        if (currentUser.isPermitted("mypermission:drive:eagle5")) {
            log.info("You are permitted to 'drive' the mypermission with license plate (id) 'eagle5'.  " +
                    "Here are the keys - have fun!");
        } else {
            log.info("Sorry, you aren't allowed to drive the 'eagle5' mypermission!");
        }

        //all done - log out!
        currentUser.logout();

        System.exit(0);
    }
}
