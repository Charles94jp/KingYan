package com.yunmuq.kingyan.controller;

import com.yunmuq.kingyan.config.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yunmuq
 * @version v1.0.0
 * @since 2022-04-23
 * @since 1.8
 * @since spring boot 2.6.6
 */
@RestController
public class User {
    static final private String contextPath = "auth";

    @GetMapping(value = contextPath + "/getUserInfo")
    public com.yunmuq.kingyan.dto.User getUserInfo() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        com.yunmuq.kingyan.dto.User user = ((CustomUserDetails) userDetails).getUser();
        user.setPassword(null);
        return user;
    }
}
