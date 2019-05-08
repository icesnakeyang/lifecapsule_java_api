package com.gogoyang.lifecapsule.utility;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
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
}
