package com.yunmuq.kingyan.util.sm;

import com.yunmuq.kingyan.util.gmhelper.SM3Util;
import org.bouncycastle.crypto.generators.OpenSSLPBEParametersGenerator;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.*;

class SMCryptPasswordEncoderTest {

    @Test
    public void test() {
        String[] r="123$$fgh".split("\\$\\$");
        byte[] r1=(new SecureRandom()).generateSeed(16);
        byte[] r2= SM3Util.hash("345tfsdfygdfhtyi346t43566789ijuyfghdbsdft234wterfsdv123wefsd78696udhdfhf".getBytes());
        byte[] r3= SM3Util.hash("345tfsdfygdfhtyi78696udhf".getBytes());
        byte[] r4= SM3Util.hash("3".getBytes());

        System.out.println(Base64.toBase64String((new SecureRandom()).generateSeed(16)));
        System.out.println(Hex.toHexString((new SecureRandom()).generateSeed(16)));
    }

}