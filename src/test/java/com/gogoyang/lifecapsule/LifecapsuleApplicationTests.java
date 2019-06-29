package com.gogoyang.lifecapsule;

import com.gogoyang.lifecapsule.utility.GogoTools;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class LifecapsuleApplicationTests {


    @Test
    public void jdkRSA() {
        try {
            Map keys = GogoTools.generateRSAKey();
            System.out.println("public key:" + keys.get("publicKey"));
            System.out.println("private key:" + keys.get("privateKey"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
