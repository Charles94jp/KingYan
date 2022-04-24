package com.yunmuq.kingyan.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;

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

    @Autowired
    HttpHeaderFilter httpHeaderFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(smCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 链式编程
        // antMatchers一定得是/xx/**，如果是结尾的接口名/hello，则请求后加上/，可以未授权访问
        http.csrf().ignoringAntMatchers("/login", "/logout").csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        http.authorizeRequests()
                .antMatchers("/all/**", "/auth/**").hasAnyRole("admin", "test")
                .antMatchers("/admin/**").hasAnyRole("admin")
                .antMatchers("/test/**").hasAnyRole("test");

        // 没有formLogin就是403，默认无权限跳转到spring boot 自带 的登录页面，后端接口是/login
        http.formLogin().loginProcessingUrl("/login").failureHandler(loginFailureHandler)
                .successHandler(loginSuccessHandler);

        http.logout();
        // 无权限返回403，而不是上面配置的login页面
        http.exceptionHandling().authenticationEntryPoint(new Http401UnauthorizedEntryPoint());

        http.addFilterBefore(httpHeaderFilter, CsrfFilter.class);

        // todo: csrf和session缓存，图片验证码
    }
}
