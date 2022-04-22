package com.yunmuq.kingyan.config.security;

import com.sun.istack.internal.NotNull;
import com.yunmuq.kingyan.config.LoginConfigEntity;
import com.yunmuq.kingyan.util.LogUtil;
import com.yunmuq.kingyan.util.gmhelper.SM3Util;
import com.yunmuq.kingyan.util.sm.SMCrypto;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.DecoderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Arrays;

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
@Component
public class SMCryptPasswordEncoder implements PasswordEncoder {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LoginConfigEntity loginConfigEntity;

    /**
     * 盐值长度：设置为和sm3 hash结果长度相同
     */
    private static final int saltLength = 32;

    private byte[] decryptSM2(CharSequence rawPassword) {
        try {
            return SMCrypto.SM2.doDecrypt((String) rawPassword, loginConfigEntity.getPrivateKeyParameters());
        } catch (InvalidCipherTextException e) {
            logger.info("SM2Util.decrypt error，用户提交的密码格式错误，服务器可能受到攻击{}", LogUtil.LogCurrentFileAndLine());
        } catch (DecoderException e) {
            logger.info("SM2Util.decrypt error，用户提交的密码非HexStr，服务器可能受到攻击{}", LogUtil.LogCurrentFileAndLine());
        }
        return null;
    }

    /**
     * sm3 hash前的字节数组，前32位是盐值，后32位是sm3编码结果
     *
     * @param rawPassword 前端SM2加密后的密文
     * @return rawPassword base64编码，null if error
     */
    @Override
    public String encode(CharSequence rawPassword) {
        byte[] pwdPlain = decryptSM2(rawPassword);
        if (pwdPlain == null) return null;

        // 生成盐值，和sm3 hash结果长度相同
        byte[] salt = (new SecureRandom()).generateSeed(saltLength);

        // 加盐：盐值+密码
        byte[] join = new byte[saltLength + pwdPlain.length];
        System.arraycopy(salt, 0, join, 0, saltLength);
        System.arraycopy(pwdPlain, 0, join, saltLength, pwdPlain.length);
        byte[] sm3Hash = SM3Util.hash(join);

        if (sm3Hash.length != saltLength) {
            logger.error("SMCryptPasswordEncoder加密用户密码错误",
                    new Exception("SM3密文长度和盐值长度不相等"));
            return null;
        }
        // 把盐值也保存，才能校验
        byte[] result = new byte[saltLength * 2];
        System.arraycopy(salt, 0, result, 0, saltLength);
        System.arraycopy(sm3Hash, 0, result, saltLength, saltLength);

        return Base64.toBase64String(result);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        // 用户不存在，即使UserDetailsService抛出UsernameNotFoundException依旧调用此方法
        if (encodedPassword == null) return false;

        byte[] pwdPlain = decryptSM2(rawPassword);
        if (pwdPlain == null) return false;

        byte[] encodedPasswordSM3Hash = Base64.decode(encodedPassword);
        if (encodedPasswordSM3Hash.length != saltLength * 2) {
            logger.debug("SMCryptPasswordEncoder密码密文encodedPassword格式错误，SM3密文长度和盐值长度不相等，可能是数据库被污染{}",
                    LogUtil.LogCurrentFileAndLine());
            return false;
        }
        // 前面是盐值，后面是真的hash
        byte[] realEncodedPasswordSM3Hash = new byte[saltLength];
        System.arraycopy(encodedPasswordSM3Hash, saltLength, realEncodedPasswordSM3Hash, 0, saltLength);

        byte[] join = new byte[saltLength + pwdPlain.length];
        System.arraycopy(encodedPasswordSM3Hash, 0, join, 0, saltLength);
        System.arraycopy(pwdPlain, 0, join, saltLength, pwdPlain.length);

        byte[] sm3Hash = SM3Util.hash(join);
        return Arrays.equals(sm3Hash, realEncodedPasswordSM3Hash);
    }
}
