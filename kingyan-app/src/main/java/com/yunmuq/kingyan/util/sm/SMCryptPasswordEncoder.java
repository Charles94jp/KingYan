package com.yunmuq.kingyan.util.sm;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 配合前端的加解密库，供SpringBootSecurity使用
 * SpringBootSecurity在命名进入数据库前要求加密，它默认的登录是密码明文传输到后端的
 * 但是我们的要求更高，我们需要密码加密传输到后端
 *
 * <b>前后端加密策略：</b>
 * 后端将配置好的SM2公钥发送给前端,前端将用户密码SM2加密后发送给后端
 * 后端{@link #encode}负责SM2解密，再随机生成盐值，拼接出盐值+密码，用SM3加密，再拼接出盐值+SM3Hash，存入数据库
 * 后端{@link #matches}校验encode前端传值与数据库的值是否一致，先SM2解密，从数据密码中提取出盐值与SM3Hash，用盐值加密明文密码，对比Hash
 *
 * @author yunmuq
 * @version v1.0.0
 * @since 2022-04-21
 * @since 1.8
 * @since spring boot 2.6.6
 */
public class SMCryptPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return null;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return false;
    }
}
