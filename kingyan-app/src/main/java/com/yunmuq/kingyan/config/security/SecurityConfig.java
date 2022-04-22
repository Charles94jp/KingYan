package com.yunmuq.kingyan.config.security;

import com.alibaba.fastjson.JSON;
import com.yunmuq.kingyan.model.response.CommonErrorResponse;
import com.yunmuq.kingyan.model.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.io.PrintWriter;

/**
 * @author yunmuq
 * @version v1.0.0
 * @since 2022-04-16
 * @since 1.8
 * @since spring boot 2.6.6
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    SMCryptPasswordEncoder smCryptPasswordEncoder;

    @Autowired
    LoginFailureHandler loginFailureHandler;

    @Autowired
    LoginSuccessHandler loginSuccessHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(smCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 链式编程
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/hello1").hasAnyRole("vip1");

        // 没有formLogin就是403，默认无权限跳转到spring boot 自带 的登录页面，后端接口是/login
        http.formLogin().loginProcessingUrl("/login").failureHandler(loginFailureHandler)
                .successHandler(loginSuccessHandler);
        // 无权限返回403，而不是上面配置的login页面
        // http.exceptionHandling().authenticationEntryPoint(new Http401UnauthorizedEntryPoint());

        // todo: csrf和session缓存，图片验证码
    }
}
