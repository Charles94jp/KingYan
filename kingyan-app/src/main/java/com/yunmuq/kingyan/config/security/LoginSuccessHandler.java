package com.yunmuq.kingyan.config.security;

import com.alibaba.fastjson.JSON;
import com.yunmuq.kingyan.model.response.LoginResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author yunmuq
 * @version v1.0.0
 * @since 2022-04-23
 * @since 1.8
 * @since spring boot 2.6.6
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setSuccess(true);
        loginResponse.setCsrfToken("asdgsadfger");
        // todo: csrf

        out.write(JSON.toJSONString(loginResponse));
    }
}
