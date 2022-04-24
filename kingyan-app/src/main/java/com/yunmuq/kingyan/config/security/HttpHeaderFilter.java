package com.yunmuq.kingyan.config.security;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 1. Add Access-Control-Allow-Origin to response
 *
 * @author yunmuq
 * @version v1.0.0
 * @since 2022-04-20
 * @since 1.8
 * @since spring boot 2.6.6
 */
@Component
public class HttpHeaderFilter implements Filter {
    @Value("${http-header.access-control-allow-origin}")
    private String addAccessControlAllowOrigin;

    @Value("${http-header.allow-CORS}")
    private boolean allowCORS;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String origin = request.getHeader("Origin");
        if (addAccessControlAllowOrigin != null) {
            response.setHeader("Access-Control-Allow-Origin", addAccessControlAllowOrigin);
        }else if (allowCORS){
            response.setHeader("Access-Control-Allow-Origin", origin);
        }
        response.setHeader("Access-Control-Allow-Credentials", "true");

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
