package com.yunmuq.kingyan.controller;

import com.yunmuq.kingyan.config.LoginConfigEntity;
import com.yunmuq.kingyan.model.response.LoginConfigResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yunmuq
 * @version v1.0.0
 * @since 2022-04-20
 * @since 1.8
 * @since spring boot 2.6.6
 */
@RestController
public class Login {
    @Autowired
    LoginConfigEntity loginConfig;

    @GetMapping("/getLoginConfig")
    public LoginConfigResponse getLoginConfig() {
        LoginConfigResponse loginConfigResponse = new LoginConfigResponse(loginConfig.getPublicKeyHex());
        return loginConfigResponse;
    }
}
