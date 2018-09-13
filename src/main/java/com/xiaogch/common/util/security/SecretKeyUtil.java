package com.xiaogch.common.util.security;

import com.xiaogch.common.util.ByteUtil;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/9/12 17:48 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class SecretKeyUtil {

    /**
     * 生成128位的AES秘钥
     *
     * @throws NoSuchAlgorithmException
     */
    public static String getAESSecretKey128() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        return getSecretKey(keyGenerator);
    }

    /**
     * 生成192位的AES秘钥
     *
     * @throws NoSuchAlgorithmException
     */
    public static String getAESSecretKey192() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(192);
        return getSecretKey(keyGenerator);
    }

    /**
     * 生成256位的AES秘钥
     *
     * @throws NoSuchAlgorithmException
     */
    public static String getAESSecretKey256() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        return getSecretKey(keyGenerator);
    }

    /**
     * 生成DES秘钥，DES keySize must be equal to 56
     *
     * @return
     *
     * @throws NoSuchAlgorithmException
     */
    public static String getDESSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        return getSecretKey(keyGenerator);
    }

    /**
     * 生成ARCFOUR秘钥
     *
     * @param keyLength 秘钥长度，且只能40-1024只能在之间，包括40 和 1024
     *
     * @throws NoSuchAlgorithmException
     */
    public static String getARCFOURSecretKey(int keyLength) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("ARCFOUR");
        keyGenerator.init(keyLength);
        return getSecretKey(keyGenerator);
    }


    /**
     * 生成Blowfish秘钥
     *
     * @param keyLength 秘钥长度，8的倍数，且只能32-448只能在之间，包括32 和 448
     *
     * @throws NoSuchAlgorithmException
     */
    public static String getBlowfishSecretKey(int keyLength) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("Blowfish");
        keyGenerator.init(keyLength);
        return getSecretKey(keyGenerator);
    }


    /**
     * 生成112 - Blowfish秘钥
     *
     * @throws NoSuchAlgorithmException
     */
    public static String getDESedeSecretKey112() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
        keyGenerator.init(112);
        return getSecretKey(keyGenerator);
    }

    /**
     * 生成168 - Blowfish秘钥
     *
     * @throws NoSuchAlgorithmException
     */
    public static String getDESedeSecretKey168() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
        keyGenerator.init(168);
        return getSecretKey(keyGenerator);
    }

    /**
     * 生成HmacMD5秘钥
     * @param keySize 秘钥长度，大于0的整数
     * @throws NoSuchAlgorithmException
     */
    public static String getHmacMD5SecretKey(int keySize) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");
        keyGenerator.init(keySize);
        return getSecretKey(keyGenerator);
    }

    /**
     * 生成HmacSHA1秘钥
     * @param keySize 秘钥长度，大于0的整数
     * @throws NoSuchAlgorithmException
     */
    public static String getHmacSHA1SecretKey(int keySize) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA1");
        keyGenerator.init(keySize);
        return getSecretKey(keyGenerator);
    }


    /**
     * 生成HmacSHA224秘钥
     * @param keySize 秘钥长度，大于等于40的整数
     * @throws NoSuchAlgorithmException
     */
    public static String getHmacSHA224SecretKey(int keySize) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA224");
        keyGenerator.init(keySize);
        return getSecretKey(keyGenerator);
    }

    /**
     * 生成HmacSHA384秘钥
     * @param keySize 秘钥长度，大于等于40的整数
     * @throws NoSuchAlgorithmException
     */
    public static String getHmacSHA384SecretKey(int keySize) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA384");
        keyGenerator.init(keySize);
        return getSecretKey(keyGenerator);
    }

    /**
     * 生成HmacSHA512秘钥
     * @param keySize 秘钥长度，大于等于40的整数
     * @throws NoSuchAlgorithmException
     */
    public static String getHmacSHA512SecretKey(int keySize) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA512");
        keyGenerator.init(keySize);
        return getSecretKey(keyGenerator);
    }

    private static String getSecretKey(KeyGenerator keyGenerator) {
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] bytes = secretKey.getEncoded();
        return ByteUtil.encodeHex(bytes);
    }


    /**
     * 生成DiffieHellman秘钥对
     * @param keySize 秘钥长度，64的倍数，且只能是512-2048，包括512和2048
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair getDiffieHellmanKeyPair(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DiffieHellman");
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 生成DSA秘钥对
     * @param keySize 秘钥长度，512 到 1024（包括两者）之间，且是 8 的倍数。
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair getDSAKeyPair(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 生成RSA秘钥对
     * @param keySize 秘钥长度，必须大于等于512
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair getRSAKeyPair(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.generateKeyPair();
    }


    /**
     * 生成EC秘钥对
     * @param keySize 秘钥长度，必须大于等于112，且小于等于571
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair getECKeyPair(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 获取公钥
     * @param keyPair
     * @return
     */
    public static String getPublicKey(KeyPair keyPair){
        return ByteUtil.encodeHex(keyPair.getPublic().getEncoded());
    }

    /**
     * 获取私钥
     * @param keyPair
     * @return
     */
    public static String getPriviteKey(KeyPair keyPair){
        return ByteUtil.encodeHex(keyPair.getPrivate().getEncoded());
    }

//    public static void main(String[] args) throws NoSuchAlgorithmException {
//
//        String key;
//        key = SecretKeyUtil.getAESSecretKey128();
//        System.out.println("key=" + key + " length=" + key.length() * 4);
//        key = SecretKeyUtil.getAESSecretKey192();
//        System.out.println("key=" + key + " length=" + key.length() * 4);
//        key = SecretKeyUtil.getAESSecretKey256();
//        System.out.println("key=" + key + " length=" + key.length() * 4);
//
//        System.out.println("============ DES ================");
//        key = SecretKeyUtil.getDESSecretKey();
//        System.out.println("key=" + key + " length=" + key.length() * 4);
//
//        System.out.println("============ ARCFOUR =========== ");
//        key = SecretKeyUtil.getARCFOURSecretKey(40);
//        System.out.println("key=" + key + " length=" + key.length() * 4);
//        key = SecretKeyUtil.getARCFOURSecretKey(1000);
//        System.out.println("key=" + key + " length=" + key.length() * 4);
//
//        System.out.println("============ Blowfish =========== ");
//        key = SecretKeyUtil.getBlowfishSecretKey(32);
//        System.out.println("key=" + key + " length=" + key.length() * 4);
//        key = SecretKeyUtil.getBlowfishSecretKey(448);
//        System.out.println("key=" + key + " length=" + key.length() * 4);
//
//
//        System.out.println("============ DESede =========== ");
//        key = SecretKeyUtil.getDESedeSecretKey112();
//        System.out.println("key=" + key + " length=" + key.length() * 4);
//        key = SecretKeyUtil.getDESedeSecretKey168();
//        System.out.println("key=" + key + " length=" + key.length() * 4);
//
//        System.out.println("============ HmacMD5 =========== ");
//        key = SecretKeyUtil.getHmacMD5SecretKey(1);
//        System.out.println("key=" + key + " length=" + key.length() * 4);
//
//        key = SecretKeyUtil.getHmacMD5SecretKey(1000232302);
//        System.out.println("key=" + key + " length=" + key.length() * 4);
//
//
//        System.out.println("============ HmacSHA1 =========== ");
//        key = SecretKeyUtil.getHmacSHA1SecretKey(1);
//        System.out.println("key=" + key + " length=" + key.length() * 4);
//
//        key = SecretKeyUtil.getHmacSHA1SecretKey(1000232302);
//        System.out.println("key=" + key + " length=" + key.length() * 4);
//
//
//        System.out.println("============ HmacSHA224 =========== ");
//        key = SecretKeyUtil.getHmacSHA224SecretKey(40);
//        System.out.println("key=" + key + " length=" + key.length() * 4);
//        key = SecretKeyUtil.getHmacSHA224SecretKey(1000232302);
//        System.out.println("key=" + key + " length=" + key.length() * 4);
//
//        System.out.println("============ HmacSHA384 =========== ");
//        key = SecretKeyUtil.getHmacSHA384SecretKey(40);
//        System.out.println("key=" + key + " length=" + key.length() * 4);
//        key = SecretKeyUtil.getHmacSHA384SecretKey(1000232302);
//        System.out.println("key=" + key + " length=" + key.length() * 4);
//
//
//        System.out.println("============ HmacSHA512 =========== ");
//        key = SecretKeyUtil.getHmacSHA512SecretKey(40);
//        System.out.println("key=" + key + " length=" + key.length() * 4);
//
//        key = SecretKeyUtil.getHmacSHA512SecretKey(1000232302);
//        System.out.println("key=" + key + " length=" + key.length() * 4);
//
//        KeyPair keyPair;
//
//        System.out.println("============ DiffieHellman =========== ");
//        keyPair = SecretKeyUtil.getDiffieHellmanKeyPair(512);
//        System.out.println("public key=" + getPublicKey(keyPair) + " length=" + getPublicKey(keyPair).length() * 4);
//        System.out.println("private key=" + getPriviteKey(keyPair) + " length=" + getPriviteKey(keyPair).length() * 4);
//
//        keyPair = SecretKeyUtil.getDiffieHellmanKeyPair(576);
//        System.out.println("public key=" + getPublicKey(keyPair) + " length=" + getPublicKey(keyPair).length() * 4);
//        System.out.println("private key=" + getPriviteKey(keyPair) + " length=" + getPriviteKey(keyPair).length() * 4);
//
//        keyPair = SecretKeyUtil.getDiffieHellmanKeyPair(2048);
//        System.out.println("public key=" + getPublicKey(keyPair) + " length=" + getPublicKey(keyPair).length() * 4);
//        System.out.println("private key=" + getPriviteKey(keyPair) + " length=" + getPriviteKey(keyPair).length() * 4);
//
//        System.out.println("============ DSA =========== ");
//        keyPair = SecretKeyUtil.getDSAKeyPair(512);
//        System.out.println("public key=" + getPublicKey(keyPair) + " length=" + getPublicKey(keyPair).length() * 4);
//        System.out.println("private key=" + getPriviteKey(keyPair) + " length=" + getPriviteKey(keyPair).length() * 4);
//
//        keyPair = SecretKeyUtil.getDSAKeyPair(1024);
//        System.out.println("public key=" + getPublicKey(keyPair) + " length=" + getPublicKey(keyPair).length() * 4);
//        System.out.println("private key=" + getPriviteKey(keyPair) + " length=" + getPriviteKey(keyPair).length() * 4);
//
//        System.out.println("============ RSA =========== ");
//        keyPair = SecretKeyUtil.getRSAKeyPair(513);
//        System.out.println("public key=" + getPublicKey(keyPair) + " length=" + getPublicKey(keyPair).length() * 4);
//        System.out.println("private key=" + getPriviteKey(keyPair) + " length=" + getPriviteKey(keyPair).length() * 4);
//
//        keyPair = SecretKeyUtil.getRSAKeyPair(1024);
//        System.out.println("public key=" + getPublicKey(keyPair) + " length=" + getPublicKey(keyPair).length() * 4);
//        System.out.println("private key=" + getPriviteKey(keyPair) + " length=" + getPriviteKey(keyPair).length() * 4);
//
//        System.out.println("============ EC =========== ");
//        keyPair = SecretKeyUtil.getECKeyPair(113);
//        System.out.println("public key=" + getPublicKey(keyPair) + " length=" + getPublicKey(keyPair).length() * 4);
//        System.out.println("private key=" + getPriviteKey(keyPair) + " length=" + getPriviteKey(keyPair).length() * 4);
//
//        keyPair = SecretKeyUtil.getECKeyPair(571);
//        System.out.println("public key=" + getPublicKey(keyPair) + " length=" + getPublicKey(keyPair).length() * 4);
//        System.out.println("private key=" + getPriviteKey(keyPair) + " length=" + getPriviteKey(keyPair).length() * 4);
//
//    }
}
