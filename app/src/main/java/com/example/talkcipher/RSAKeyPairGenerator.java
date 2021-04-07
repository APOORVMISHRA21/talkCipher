package com.example.talkcipher;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class RSAKeyPairGenerator {


    RSAKeyPairGenerator(){}

    public static KeyPair getKeyPair(){
        KeyPair kp = null;
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            kp = kpg.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return kp;
    }
}
