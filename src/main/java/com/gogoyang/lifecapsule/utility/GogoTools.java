package com.gogoyang.lifecapsule.utility;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.omg.CORBA.PUBLIC_MEMBER;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GogoTools {
    /**
     * 生成一个UUID
     *
     * @return
     */
    public static UUID UUID() throws Exception {
        UUID uuid = UUID.randomUUID();
        return uuid;
    }

    /**
     * 对用户密码进行MD5加密
     *
     * @param password
     * @return
     * @throws Exception
     */
    public static String encoderByMd5(String password) throws Exception {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newpass = base64en.encode(md5.digest(password.getBytes("utf-8")));
        return newpass;
    }


    /**
     * 生成一个AES秘钥
     *
     * @return
     * @throws Exception
     */
    public static String generateAESKey() throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(256);
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");

        return Base64.encode(key.getEncoded());
    }

    /**
     * 生成一对RSA公钥和私钥
     *
     * @return
     * @throws Exception
     */
    public static Map generateRSAKey() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(512);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map out = new HashMap();
        out.put("publicKey", Base64.encode(rsaPublicKey.getEncoded()));
        out.put("privateKey", Base64.encode(rsaPrivateKey.getEncoded()));
        return out;
    }

    public static Map encryptRSA(String src, RSAPrivateKey rsaPrivateKey) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(src.getBytes());
        Map out = new HashMap();
        out.put("result", result);
        return out;
    }

    public static Map decryptRSA(String src, RSAPublicKey rsaPublicKey) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] result = cipher.doFinal(src.getBytes());
        Map out = new HashMap();
        out.put("result", result);
        return out;
    }
}
