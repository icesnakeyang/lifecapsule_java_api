package com.gogoyang.lifecapsule;

import com.mysql.cj.util.Base64Decoder;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.security.krb5.internal.crypto.Aes128;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


@RunWith(SpringRunner.class)
@SpringBootTest
public class LifecapsuleApplicationTests {
    private static String src = "imooc security rsa";

    @Test
    public void jdkRSA() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(512);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
            System.out.println("明文：" + src);
            String strPublicKey = Base64.encode(rsaPublicKey.getEncoded());
            String strPrivateKey = Base64.encode(rsaPrivateKey.getEncoded());
            System.out.println("公钥：" + strPublicKey);
            System.out.println("私钥：" + strPrivateKey);

            //私钥加密，公钥解密——加密
            byte[] keyBytes;
            keyBytes = Base64.decode(strPrivateKey.getBytes());
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decode(strPrivateKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] result = cipher.doFinal(src.getBytes());
            String strETByPrivateKey = Base64.encode(result);
            System.out.println("私钥加密后的密文:" + strETByPrivateKey);

            //私钥加密，公钥解密——解密
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decode(strPublicKey.getBytes()));
            keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            result = cipher.doFinal(Base64.decode(strETByPrivateKey.getBytes()));
            String srcResult = new String(result);
            System.out.println("私钥加密，公钥解密——解密：" + srcResult);

            //公钥加密，私钥解密——加密
            x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decode(strPublicKey.getBytes()));
            keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            result = cipher.doFinal(src.getBytes());
            String strResult = Base64.encode(result);
            System.out.println("公钥加密：" + strResult);

            //公钥加密，私钥解密——解密
            pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decode(strPrivateKey.getBytes()));
            keyFactory = keyFactory.getInstance("RSA");
            privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            result = cipher.doFinal(Base64.decode(strResult.getBytes()));
            strResult = new String(result);
            System.out.println("公钥加密，私钥解密，解密：" + strResult);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void jdkAES() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] keyBytes = secretKey.getEncoded();

            String AESKey = Base64.encode(keyBytes);
            System.out.println("aes key:" + AESKey);
            Key key = new SecretKeySpec(Base64.decode(AESKey), "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal(src.getBytes());
            String codec = Base64.encode(result);
            System.out.println("jdk aes encrypt:" + codec);

            cipher.init(Cipher.DECRYPT_MODE, key);
            result = cipher.doFinal(Base64.decode(codec));
            System.out.println("jdk aes destrypt:" + new String(result));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
