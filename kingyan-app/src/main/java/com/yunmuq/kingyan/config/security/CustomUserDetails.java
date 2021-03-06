package com.yunmuq.kingyan.config.security;

import com.yunmuq.kingyan.dto.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author yunmuq
 * @version v1.0.0
 * @since 2022-04-21
 * @since 1.8
 * @since spring boot 2.6.6
 */
public class CustomUserDetails implements UserDetails {
    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    public CustomUserDetails() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 一个用户只有一个角色
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // todo: 用户角色
        ArrayList<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        // `hasRole()`传入角色名，会默认给参数添加`ROLE_`前缀
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName()));
        return simpleGrantedAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
