package com.xiaogch.common.util.security;

import com.xiaogch.common.util.StringFormat;

import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/9/14 18:02 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class SecretKeyUtilTest {
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        String key;
        key = SecretKeyUtil.getAESSecretKey128(StringFormat.HEX);
        System.out.println("key=" + key + " length=" + key.length() * 4);
        key = SecretKeyUtil.getAESSecretKey192(StringFormat.HEX);
        System.out.println("key=" + key + " length=" + key.length() * 4);
        key = SecretKeyUtil.getAESSecretKey256(StringFormat.HEX);
        System.out.println("key=" + key + " length=" + key.length() * 4);

        System.out.println("============ DES ================");
        key = SecretKeyUtil.getDESSecretKey56(StringFormat.HEX);
        System.out.println("key=" + key + " length=" + key.length() * 4);

        System.out.println("============ ARCFOUR =========== ");
        key = SecretKeyUtil.getARCFOURSecretKey(40 , StringFormat.HEX);
        System.out.println("key=" + key + " length=" + key.length() * 4);
        key = SecretKeyUtil.getARCFOURSecretKey(1000, StringFormat.HEX);
        System.out.println("key=" + key + " length=" + key.length() * 4);

        System.out.println("============ Blowfish =========== ");
        key = SecretKeyUtil.getBlowfishSecretKey(32, StringFormat.HEX);
        System.out.println("key=" + key + " length=" + key.length() * 4);
        key = SecretKeyUtil.getBlowfishSecretKey(448, StringFormat.HEX);
        System.out.println("key=" + key + " length=" + key.length() * 4);


        System.out.println("============ DESede =========== ");
        key = SecretKeyUtil.getDESedeSecretKey112(StringFormat.HEX);
        System.out.println("key=" + key + " length=" + key.length() * 4);
        key = SecretKeyUtil.getDESedeSecretKey168(StringFormat.HEX);
        System.out.println("key=" + key + " length=" + key.length() * 4);

        System.out.println("============ HmacMD5 =========== ");
        key = SecretKeyUtil.getHmacMD5SecretKey(1, StringFormat.HEX);
        System.out.println("key=" + key + " length=" + key.length() * 4);

        key = SecretKeyUtil.getHmacMD5SecretKey(1000232302, StringFormat.HEX);
        System.out.println("key=" + key + " length=" + key.length() * 4);


        System.out.println("============ HmacSHA1 =========== ");
        key = SecretKeyUtil.getHmacSHA1SecretKey(1, StringFormat.HEX);
        System.out.println("key=" + key + " length=" + key.length() * 4);

        key = SecretKeyUtil.getHmacSHA1SecretKey(1000232302, StringFormat.HEX);
        System.out.println("key=" + key + " length=" + key.length() * 4);


        System.out.println("============ HmacSHA224 =========== ");
        key = SecretKeyUtil.getHmacSHA224SecretKey(40, StringFormat.HEX);
        System.out.println("key=" + key + " length=" + key.length() * 4);
        key = SecretKeyUtil.getHmacSHA224SecretKey(1000232302, StringFormat.HEX);
        System.out.println("key=" + key + " length=" + key.length() * 4);

        System.out.println("============ HmacSHA384 =========== ");
        key = SecretKeyUtil.getHmacSHA384SecretKey(40, StringFormat.HEX);
        System.out.println("key=" + key + " length=" + key.length() * 4);
        key = SecretKeyUtil.getHmacSHA384SecretKey(1000232302, StringFormat.HEX);
        System.out.println("key=" + key + " length=" + key.length() * 4);


        System.out.println("============ HmacSHA512 =========== ");
        key = SecretKeyUtil.getHmacSHA512SecretKey(40, StringFormat.HEX);
        System.out.println("key=" + key + " length=" + key.length() * 4);

        key = SecretKeyUtil.getHmacSHA512SecretKey(1000232302, StringFormat.HEX);
        System.out.println("key=" + key + " length=" + key.length() * 4);

        KeyPair keyPair;

        System.out.println("============ DiffieHellman =========== ");
        keyPair = SecretKeyUtil.getDiffieHellmanKeyPair(512);
        System.out.println("public key=" + SecretKeyUtil.getPublicKey(keyPair, StringFormat.HEX) + " length=" + SecretKeyUtil.getPublicKey(keyPair , StringFormat.HEX).length() * 4);
        System.out.println("private key=" + SecretKeyUtil.getPrivateKey(keyPair, StringFormat.HEX) + " length=" + SecretKeyUtil.getPrivateKey(keyPair, StringFormat.HEX).length() * 4);

        keyPair = SecretKeyUtil.getDiffieHellmanKeyPair(576);
        System.out.println("public key=" + SecretKeyUtil.getPublicKey(keyPair, StringFormat.HEX) + " length=" + SecretKeyUtil.getPublicKey(keyPair, StringFormat.HEX).length() * 4);
        System.out.println("private key=" + SecretKeyUtil.getPrivateKey(keyPair, StringFormat.HEX) + " length=" + SecretKeyUtil.getPrivateKey(keyPair, StringFormat.HEX).length() * 4);

        keyPair = SecretKeyUtil.getDiffieHellmanKeyPair(2048);
        System.out.println("public key=" + SecretKeyUtil.getPublicKey(keyPair, StringFormat.HEX) + " length=" + SecretKeyUtil.getPublicKey(keyPair, StringFormat.HEX).length() * 4);
        System.out.println("private key=" + SecretKeyUtil.getPrivateKey(keyPair, StringFormat.HEX) + " length=" + SecretKeyUtil.getPrivateKey(keyPair, StringFormat.HEX).length() * 4);

        System.out.println("============ DSA =========== ");
        keyPair = SecretKeyUtil.getDSAKeyPair(512);
        System.out.println("public key=" + SecretKeyUtil.getPublicKey(keyPair, StringFormat.HEX) + " length=" + SecretKeyUtil.getPublicKey(keyPair, StringFormat.HEX).length() * 4);
        System.out.println("private key=" + SecretKeyUtil.getPrivateKey(keyPair, StringFormat.HEX) + " length=" + SecretKeyUtil.getPrivateKey(keyPair, StringFormat.HEX).length() * 4);

        keyPair = SecretKeyUtil.getDSAKeyPair(1024);
        System.out.println("public key=" + SecretKeyUtil.getPublicKey(keyPair, StringFormat.HEX) + " length=" + SecretKeyUtil.getPublicKey(keyPair, StringFormat.HEX).length() * 4);
        System.out.println("private key=" + SecretKeyUtil.getPrivateKey(keyPair, StringFormat.HEX) + " length=" + SecretKeyUtil.getPrivateKey(keyPair, StringFormat.HEX).length() * 4);

        System.out.println("============ RSA =========== ");
        keyPair = SecretKeyUtil.getRSAKeyPair(513);
        System.out.println("public key=" + SecretKeyUtil.getPublicKey(keyPair, StringFormat.HEX) + " length=" + SecretKeyUtil.getPublicKey(keyPair, StringFormat.HEX).length() * 4);
        System.out.println("private key=" + SecretKeyUtil.getPrivateKey(keyPair, StringFormat.HEX) + " length=" + SecretKeyUtil.getPrivateKey(keyPair, StringFormat.HEX).length() * 4);

        keyPair = SecretKeyUtil.getRSAKeyPair(1024);
        System.out.println("public key=" + SecretKeyUtil.getPublicKey(keyPair, StringFormat.HEX) + " length=" + SecretKeyUtil.getPublicKey(keyPair, StringFormat.HEX).length() * 4);
        System.out.println("private key=" + SecretKeyUtil.getPrivateKey(keyPair, StringFormat.HEX) + " length=" + SecretKeyUtil.getPrivateKey(keyPair, StringFormat.HEX).length() * 4);

        System.out.println("============ EC =========== ");
        keyPair = SecretKeyUtil.getECKeyPair(113);
        System.out.println("public key=" + SecretKeyUtil.getPublicKey(keyPair, StringFormat.HEX) + " length=" + SecretKeyUtil.getPublicKey(keyPair, StringFormat.HEX).length() * 4);
        System.out.println("private key=" + SecretKeyUtil.getPrivateKey(keyPair, StringFormat.HEX) + " length=" + SecretKeyUtil.getPrivateKey(keyPair, StringFormat.HEX).length() * 4);

        keyPair = SecretKeyUtil.getECKeyPair(571);
        System.out.println("public key=" + SecretKeyUtil.getPublicKey(keyPair, StringFormat.HEX) + " length=" + SecretKeyUtil.getPublicKey(keyPair, StringFormat.HEX).length() * 4);
        System.out.println("private key=" + SecretKeyUtil.getPrivateKey(keyPair, StringFormat.HEX) + " length=" + SecretKeyUtil.getPrivateKey(keyPair, StringFormat.HEX).length() * 4);

    }
}