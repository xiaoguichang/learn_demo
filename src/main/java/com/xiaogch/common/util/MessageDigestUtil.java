package com.xiaogch.common.util;

import org.springframework.util.Assert;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Author: guich <BR>
 * Version: v 1.0 <BR>
 * Date: 2017/9/26 16:55 <BR>
 * Description: 基本的签名算法封装 <BR>
 * Function List: <BR>
 */
public class MessageDigestUtil {
    private static final String ALGORITHM_MD2 = "MD2";
    private static final String ALGORITHM_MD5 = "MD5";
    private static final String ALGORITHM_SHA1 = "SHA1";
//    private static final String ALGORITHM_SHA_256 = "SHA-256";
//    private static final String ALGORITHM_SHA_384 = "SHA-384";
//    private static final String ALGORITHM_SHA_512 = "SHA-512";

    private static final String ALGORITHM_HMACMD2 = "HmacMD2";
//    private static final String ALGORITHM_HMACMD5 = "HmacMD5";
//    private static final String ALGORITHM_HMACSHA1 = "HmacSHA1";
//    private static final String ALGORITHM_HMACSHA256 = "HmacSHA256";
//    private static final String ALGORITHM_HMACSHA384 = "HmacSHA384";
//    private static final String ALGORITHM_HMACSHA512 = "HmacSHA512";

    private static final String DEFAULT_ENCODING = "UTF-8";

    public static String sha1(String source) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return sha1(source , DEFAULT_ENCODING);
    }

    public static String sha1(String source , String encoding) throws NoSuchAlgorithmException , UnsupportedEncodingException {
        Assert.notNull(source , "digest source con't be null");
        Assert.notNull(source , "digest encoding con't be null");
        byte[] sourceBytes = source.getBytes(encoding);
        return sha1(sourceBytes);
    }

    public static String sha1(byte[] source) throws NoSuchAlgorithmException {
        Assert.notNull(source , "digest source con't be null");
        MessageDigest digest = MessageDigest.getInstance(ALGORITHM_SHA1);
        byte[] result = digest.digest(source);
        return ByteUtil.encodeHex(result);
    }

    public static String md2(String source) throws NoSuchAlgorithmException , UnsupportedEncodingException {
        return md2(source , DEFAULT_ENCODING);
    }

    public static String md2(String source , String encoding) throws NoSuchAlgorithmException , UnsupportedEncodingException {
        Assert.notNull(source , "digest source con't be null");
        Assert.notNull(source , "digest encoding con't be null");
        byte[] sourceBytes = source.getBytes(encoding);
        return md2(sourceBytes);
    }

    public static String md2(byte[] source) throws NoSuchAlgorithmException {
        Assert.notNull(source , "digest source con't be null");
        MessageDigest digest = MessageDigest.getInstance(ALGORITHM_MD2);
        byte[] result = digest.digest(source);
        return ByteUtil.encodeHex(result);
    }

    public static String md5(String source) throws NoSuchAlgorithmException , UnsupportedEncodingException {
        return md5(source , DEFAULT_ENCODING);
    }

    public static String md5(String source , String encoding) throws NoSuchAlgorithmException , UnsupportedEncodingException {
        Assert.notNull(source , "digest source con't be null");
        Assert.notNull(source , "digest encoding con't be null");
        byte[] sourceBytes = source.getBytes(encoding);
        return md5(sourceBytes);
    }

    public static String md5(byte[] source) throws NoSuchAlgorithmException {
        Assert.notNull(source , "digest source con't be null");
        MessageDigest digest = MessageDigest.getInstance(ALGORITHM_MD5);
        byte[] result = digest.digest(source);
        return ByteUtil.encodeHex(result);
    }


    public static String hmacMd2(String source , String securityKey) throws NoSuchAlgorithmException , UnsupportedEncodingException , InvalidKeyException{
        return hmacMd2(source , securityKey , DEFAULT_ENCODING);
    }

    public static String hmacMd2(String source, String securityKey , String encoding) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        Assert.notNull(source , "digest source con't be null");
        Assert.notNull(source , "digest encoding con't be null");
        return hmacMd2(source.getBytes(encoding) , securityKey.getBytes(encoding));
    }

    public static String hmacMd2(byte[] source , byte[] securityKey) throws NoSuchAlgorithmException, InvalidKeyException {
        Assert.notNull(source , "digest source con't be null");
        SecretKeySpec secretKeySpec = new SecretKeySpec(securityKey , ALGORITHM_HMACMD2);
        Mac mac = Mac.getInstance(ALGORITHM_HMACMD2);
        mac.init(secretKeySpec);
        byte[] result = mac.doFinal(source);
        return ByteUtil.encodeHex(result);
    }
}
