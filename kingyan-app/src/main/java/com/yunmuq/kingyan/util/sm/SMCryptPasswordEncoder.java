package com.yunmuq.kingyan.util.sm;

import com.yunmuq.kingyan.util.gmhelper.SM3Util;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

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
    /**
     * 和sm3 hash结果长度相同
     */
    private int saltLength = 32;

    /**
     * 字节数组，前32位是盐值，后面是sm3编码结果
     *
     * @param
     * @return rawPassword base64编码
     */
    @Override
    public String encode(CharSequence rawPassword) {
        // 和sm3 hash结果长度相同
        byte[] salt = (new SecureRandom()).generateSeed(saltLength);
        byte[] pwd = ((String) rawPassword).getBytes();
        byte[] join = new byte[saltLength + pwd.length];
        System.arraycopy(salt, 0, join, 0, saltLength);
        System.arraycopy(pwd, 0, join, saltLength + 1, pwd.length);
        byte[] sm3Hash = SM3Util.hash(join);

        if (sm3Hash.length!=saltLength){
            throw new Exception("sm3");
        }
        // 把盐值也保存，才能校验
        byte[] result = new byte[saltLength * 2];
        System.arraycopy(salt, 0, result, 0, saltLength);
        System.arraycopy(sm3Hash, 0, result, saltLength + 1, saltLength);


        //System.
        return null;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return false;
    }
}
