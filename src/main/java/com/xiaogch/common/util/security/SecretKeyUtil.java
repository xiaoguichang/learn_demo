package com.xiaogch.common.util.security;

import com.xiaogch.common.util.ByteUtil;
import com.xiaogch.common.util.StringFormat;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * 秘钥工具类 支持一下算法:<br/>
 * 1、AES , DES , ARCFOUR , DESede <br/>
 * 2、HmacMD5 , HmacSHA1 , HmacSHA224 , HmacSHA256 , HmacSHA384 , HmacSHA512<br/>
 */
public class SecretKeyUtil {
    private static final String AES = "AES";
    private static final String DES = "DES";
    private static final String ARCFOUR = "ARCFOUR";
    private static final String BLOWFISH = "Blowfish";
    private static final String DES_EDE = "DESede";
    private static final String HMAC_MD5 = "HmacMD5";
    private static final String HMAC_SHA_1 = "HmacSHA1";
    private static final String HMAC_SHA_224= "HmacSHA224";
    private static final String HMAC_SHA_384 = "HmacSHA384";
    private static final String HMAC_SHA_512 = "HmacSHA512";
    /**
     * 生成128位的AES秘钥
     * @param format 秘钥格式
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String getAESSecretKey128(StringFormat format) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
        keyGenerator.init(128);
        return getSecretKey(keyGenerator , format);
    }

    /**
     * 生成192位的AES秘钥
     * @param format 秘钥格式
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String getAESSecretKey192(StringFormat format) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
        keyGenerator.init(192);
        return getSecretKey(keyGenerator , format);
    }

    /**
     * 生成256位的AES秘钥
     * @param format 秘钥格式
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String getAESSecretKey256(StringFormat format) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
        keyGenerator.init(256);
        return getSecretKey(keyGenerator , format);
    }

    /**
     * 生成DES秘钥，DES keySize must be equal to 56
     *
     * @param format 秘钥格式
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String getDESSecretKey56(StringFormat format) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(DES);
        return getSecretKey(keyGenerator , format);
    }

    /**
     * 生成ARCFOUR秘钥
     *
     * @param keyLength 秘钥长度，且只能40-1024只能在之间，包括40 和 1024
     * @param format 秘钥格式
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String getARCFOURSecretKey(int keyLength , StringFormat format) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ARCFOUR);
        keyGenerator.init(keyLength);
        return getSecretKey(keyGenerator , format);
    }


    /**
     * 生成Blowfish秘钥
     *
     * @param keyLength 秘钥长度，8的倍数，且只能32-448只能在之间，包括32 和 448
     * @param format 秘钥格式
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String getBlowfishSecretKey(int keyLength , StringFormat format) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(BLOWFISH);
        keyGenerator.init(keyLength);
        return getSecretKey(keyGenerator , format);
    }


    /**
     * 生成112 - Blowfish秘钥
     * @param format 秘钥格式
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String getDESedeSecretKey112(StringFormat format) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(DES_EDE);
        keyGenerator.init(112);
        return getSecretKey(keyGenerator, format);
    }


    /**
     * 生成168 - Blowfish秘钥
     * @param format 秘钥格式
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String getDESedeSecretKey168(StringFormat format) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(DES_EDE);
        keyGenerator.init(168);
        return getSecretKey(keyGenerator , format);
    }


    /**
     * 生成HmacMD5秘钥
     * @param keySize 秘钥长度，大于0的整数
     * @param format 秘钥格式
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String getHmacMD5SecretKey(int keySize, StringFormat format) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(HMAC_MD5);
        keyGenerator.init(keySize);
        return getSecretKey(keyGenerator , format);
    }

    /**
     * 生成HmacSHA1秘钥
     * @param keySize 秘钥长度，大于0的整数
     * @param format 秘钥格式
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String getHmacSHA1SecretKey(int keySize , StringFormat format) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(HMAC_SHA_1);
        keyGenerator.init(keySize);
        return getSecretKey(keyGenerator , format);
    }

    /**
     * 生成HmacSHA224秘钥
     * @param keySize 秘钥长度，大于等于40的整数
     * @param format 秘钥格式
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String getHmacSHA224SecretKey(int keySize, StringFormat format) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(HMAC_SHA_224);
        keyGenerator.init(keySize);
        return getSecretKey(keyGenerator , format);
    }

    /**
     * 生成HmacSHA384秘钥
     * @param keySize 秘钥长度，大于等于40的整数
     * @param format 秘钥格式
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String getHmacSHA384SecretKey(int keySize , StringFormat format) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(HMAC_SHA_384);
        keyGenerator.init(keySize);
        return getSecretKey(keyGenerator , format);
    }


    /**
     * 生成HmacSHA512秘钥
     * @param keySize 秘钥长度，大于等于40的整数
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String getHmacSHA512SecretKey(int keySize , StringFormat format)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(HMAC_SHA_512);
        keyGenerator.init(keySize);
        return getSecretKey(keyGenerator , format);
    }

    /**
     * 生成秘钥
     * @param keyGenerator 秘钥生成器
     * @param format 秘钥格式
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String getSecretKey(KeyGenerator keyGenerator , StringFormat format) throws UnsupportedEncodingException {
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] bytes = secretKey.getEncoded();
        return ByteUtil.bytesToString(bytes , format);
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
     * @param keyPair 秘钥对
     * @param format 公钥格式
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getPublicKey(KeyPair keyPair , StringFormat format) throws UnsupportedEncodingException {

        return ByteUtil.bytesToString(keyPair.getPublic().getEncoded() , format);
    }


    /**
     * 获取私钥
     * @param keyPair 秘钥对
     * @param format 私钥格式
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getPrivateKey(KeyPair keyPair , StringFormat format) throws UnsupportedEncodingException {
        return ByteUtil.bytesToString(keyPair.getPrivate().getEncoded() , format);
    }

    /**
     * 生成加密向量
     * @param format 向量格式
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getIV(StringFormat format) throws UnsupportedEncodingException {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random(System.currentTimeMillis());
        // IV must be 16 bytes long
        for (int index = 0 ; index < 16 ; index ++) {
            stringBuilder.append(ByteUtil.hexChars[random.nextInt(16)]);
        }
        byte[] bytes = ByteUtil.decodeHex(stringBuilder.toString());
        return ByteUtil.bytesToString(bytes , format);
    }


}
