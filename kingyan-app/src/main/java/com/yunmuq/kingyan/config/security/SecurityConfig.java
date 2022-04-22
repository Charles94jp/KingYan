package com.yunmuq.kingyan.config.security;

import com.yunmuq.kingyan.util.sm.SMCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(new SMCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 链式编程
        http.authorizeRequests()
                .antMatchers("/hello1").hasAnyRole("vip1")
                .and()
                .csrf().disable();

        // 无权限跳转到spring boot 自带 的登录页面，后端接口是/login
        // 没有这句就是403
        http.formLogin().loginProcessingUrl("/login");
        // 无权限返回403，而不是上面配置的login页面
        // http.exceptionHandling().authenticationEntryPoint(new Http401UnauthorizedEntryPoint());
    }
}
