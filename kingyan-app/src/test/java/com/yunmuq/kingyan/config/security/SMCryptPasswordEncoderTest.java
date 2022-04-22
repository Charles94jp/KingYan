package com.yunmuq.kingyan.config.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SMCryptPasswordEncoderTest {
    /**
     * 用application.yml中的密钥对去node.js中用sm-crypto生成密文
     */
    private final String pwd = "dfdf06bb4e8247f1f36be1ca81b0a0d78dace333baa61739cce777bd0e0d94dcf48c9f96ca4c0a4a" +
            "d1513655a9ce3a6281617ea8991ec8206e2746ea06075655865660af1362410199e100f56f91912894a2ec19a4186b1a3b3" +
            "2309a6bb3ea1d32b0a2cad5bcc77eb4";
    private final String pwd1 = "0cf1f6cdf95111612b2da3e09ad7c4eafed4a5337c5e8d9d0f8aa1bb85095dc069de4ef3a08a8f2" +
            "c70ce3cba024a4af396312f3511c6564aaeeabb252f33577b74f407c7bcdbfb1af5210f6b2ecfef6579208c3c7f7d3b7330" +
            "82f3cdd0d4771f4da2844c11f5654fbb";

    @Autowired
    private SMCryptPasswordEncoder smCryptPasswordEncoder;

    String encode(String passwd) {
        String sm3Hash = smCryptPasswordEncoder.encode(passwd);
        System.out.println(sm3Hash);
        return sm3Hash;
    }

    @Test
    void testMatches() {
        boolean result = smCryptPasswordEncoder.matches(pwd1, encode(pwd));
        System.out.println(result);
        assertEquals(true, result);
    }

    @Test
    void testEncode() {
        String sm3Hash = smCryptPasswordEncoder.encode("53c580b1f171d2bb705b0e7ce9ca1e464295754556cd303" +
                "572e07a630cbfa7aeb8effdae4fe6f7d5b1ae250a7c69d3aab3592de2c760fbfbf146bc570a68da4005069cce73b4a206" +
                "ee35d94b2e10621127d0983c25b02851f2b99a5b98d19ce9fa750eabab64624b");
        System.out.println(sm3Hash);
        assertNotNull(sm3Hash);
    }
}