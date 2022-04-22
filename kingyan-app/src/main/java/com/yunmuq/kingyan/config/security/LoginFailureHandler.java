package com.yunmuq.kingyan.config.security;

import com.alibaba.fastjson.JSON;
import com.yunmuq.kingyan.model.response.CommonErrorResponse;
import com.yunmuq.kingyan.model.response.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author yunmuq
 * @version v1.0.0
 * @since 2022-04-22
 * @since 1.8
 * @since spring boot 2.6.6
 */
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setError(loginResponse.getError() == null ? (new CommonErrorResponse()) : loginResponse.getError());
        // 深层是 UsernameNotFoundException
        if (exception instanceof BadCredentialsException) {
            // todo: i18n
            loginResponse.getError().setMessage("用户名或密码错误");
        } else {
            loginResponse.getError().setMessage("系统错误");
            logger.info("", exception);
        }
        // 其它登录异常，如禁用，被锁定：https://blog.csdn.net/llvyuanjie/article/details/105892075
        out.write(JSON.toJSONString(loginResponse));
    }
}
