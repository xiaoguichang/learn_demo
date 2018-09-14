package com.xiaogch.common.util.security;

import com.xiaogch.common.util.ByteUtil;
import org.springframework.util.Assert;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 消息摘要工具类 支持一下摘要算法:<br/>
 * 1、MD2 , MD5<br/>
 * 2、SHA1 , SHA-224 , SHA-256 , SHA-384 , SHA-512<br/>
 * 3、HmacMD5 , HmacSHA1 , HmacSHA224 , HmacSHA256 , HmacSHA384 , HmacSHA512<br/>
 */
public class MessageDigestUtil {
    private static final String ALGORITHM_MD2 = "MD2";
    private static final String ALGORITHM_MD5 = "MD5";
    private static final String ALGORITHM_SHA1 = "SHA1";
    private static final String ALGORITHM_SHA_224 = "SHA-224";
    private static final String ALGORITHM_SHA_256 = "SHA-256";
    private static final String ALGORITHM_SHA_384 = "SHA-384";
    private static final String ALGORITHM_SHA_512 = "SHA-512";

    private static final String ALGORITHM_HMAC_MD5 = "HmacMD5";
    private static final String ALGORITHM_HMAC_SHA1 = "HmacSHA1";
    private static final String ALGORITHM_HMAC_SHA224 = "HmacSHA224";
    private static final String ALGORITHM_HMAC_SHA256 = "HmacSHA256";
    private static final String ALGORITHM_HMAC_SHA384 = "HmacSHA384";
    private static final String ALGORITHM_HMAC_SHA512 = "HmacSHA512";

    private static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * 生成 sha1 消息摘要
     * @param source 明文
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String sha1(String source) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return sha1(source , DEFAULT_ENCODING);
    }

    /**
     * 生成 sha1 消息摘要
     * @param source 明文
     * @param encoding 字符集
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String sha1(String source , String encoding) throws NoSuchAlgorithmException , UnsupportedEncodingException {
        Assert.notNull(source , "digest source can't be null");
        Assert.notNull(source , "digest encoding can't be null");
        byte[] sourceBytes = source.getBytes(encoding);
        return digest(sourceBytes , ALGORITHM_SHA1);
    }

    /**
     * 生成 sha224 消息摘要
     * @param source 明文
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String sha224(String source) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return sha224(source , DEFAULT_ENCODING);
    }

    /**
     * 生成 sha224 消息摘要
     * @param source 明文
     * @param encoding 字符集
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String sha224(String source , String encoding) throws NoSuchAlgorithmException , UnsupportedEncodingException {
        Assert.notNull(source , "digest source can't be null");
        Assert.notNull(source , "digest encoding can't be null");
        byte[] sourceBytes = source.getBytes(encoding);
        return digest(sourceBytes , ALGORITHM_SHA_224);
    }

    /**
     * 生成 sha256 消息摘要
     * @param source 明文
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String sha256(String source) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return sha256(source , DEFAULT_ENCODING);
    }

    /**
     * 生成 sha256 消息摘要
     * @param source 明文
     * @param encoding 字符集
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String sha256(String source , String encoding) throws NoSuchAlgorithmException , UnsupportedEncodingException {
        Assert.notNull(source , "digest source can't be null");
        Assert.notNull(source , "digest encoding can't be null");
        byte[] sourceBytes = source.getBytes(encoding);
        return digest(sourceBytes , ALGORITHM_SHA_256);
    }


    /**
     * 生成 sha384 消息摘要
     * @param source 明文
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String sha384(String source) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return sha384(source , DEFAULT_ENCODING);
    }

    /**
     * 生成 sha384 消息摘要
     * @param source 明文
     * @param encoding 字符集
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String sha384(String source , String encoding) throws NoSuchAlgorithmException , UnsupportedEncodingException {
        Assert.notNull(source , "digest source can't be null");
        Assert.notNull(source , "digest encoding can't be null");
        byte[] sourceBytes = source.getBytes(encoding);
        return digest(sourceBytes , ALGORITHM_SHA_384);
    }

    /**
     * 生成 sha512 消息摘要
     * @param source 明文
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String sha512(String source) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return sha512(source , DEFAULT_ENCODING);
    }

    /**
     * 生成sha512 消息摘要
     * @param source 明文
     * @param encoding 字符集
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String sha512(String source , String encoding) throws NoSuchAlgorithmException , UnsupportedEncodingException {
        Assert.notNull(source , "digest source can't be null");
        Assert.notNull(source , "digest encoding can't be null");
        byte[] sourceBytes = source.getBytes(encoding);
        return digest(sourceBytes , ALGORITHM_SHA_512);
    }

    /**
     * 生成md2 消息摘要
     * @param source 明文
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String md2(String source) throws NoSuchAlgorithmException , UnsupportedEncodingException {
        return md2(source , DEFAULT_ENCODING);
    }

    /**
     * 生成md2 消息摘要
     * @param source 明文
     * @param encoding 字符集
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String md2(String source , String encoding) throws NoSuchAlgorithmException , UnsupportedEncodingException {
        Assert.notNull(source , "digest source can't be null");
        Assert.notNull(source , "digest encoding can't be null");
        byte[] sourceBytes = source.getBytes(encoding);
        return digest(sourceBytes , ALGORITHM_MD2);
    }


    /**
     * 生成md5 消息摘要
     * @param source 明文
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String md5(String source) throws NoSuchAlgorithmException , UnsupportedEncodingException {
        return md5(source , DEFAULT_ENCODING);
    }

    /**
     * 生成md5 消息摘要
     * @param source 明文
     * @param encoding 字符集
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String md5(String source , String encoding) throws NoSuchAlgorithmException , UnsupportedEncodingException {
        Assert.notNull(source , "digest source can't be null");
        Assert.notNull(source , "digest encoding can't be null");
        byte[] sourceBytes = source.getBytes(encoding);
        return digest(sourceBytes , ALGORITHM_MD5);
    }

    /**
     * 生成消息摘要
     * @param source 明文
     * @param algorithm 摘要算法
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    private static String digest(byte[] source , String algorithm) throws NoSuchAlgorithmException {
        Assert.notNull(source , "digest source can't be null");
        Assert.notNull(algorithm , "digest algorithm can't be null");
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        byte[] result = digest.digest(source);
        return ByteUtil.encodeHex(result);
    }


    /**
     * 生成 hmacMd5 消息摘要
     * @param source 明文
     * @param securityKey 摘要算法
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String hmacMd5(String source , String securityKey) throws NoSuchAlgorithmException , UnsupportedEncodingException , InvalidKeyException{
        return hmacMd5(source , securityKey , DEFAULT_ENCODING);
    }

    /**
     * 生成 hmacMd5 消息摘要
     * @param source 明文
     * @param securityKey 秘钥
     * @param encoding 字符集
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String hmacMd5(String source, String securityKey , String encoding) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        Assert.notNull(source , "digest source can't be null");
        Assert.notNull(source , "digest encoding can't be null");
        return digestByMac(source.getBytes(encoding) , securityKey.getBytes(encoding) , ALGORITHM_HMAC_MD5);
    }


    /**
     * 生成 hmacSha1 消息摘要
     * @param source 明文
     * @param securityKey 摘要算法
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String hmacSha1(String source , String securityKey) throws NoSuchAlgorithmException , UnsupportedEncodingException , InvalidKeyException{
        return hmacSha1(source , securityKey , DEFAULT_ENCODING);
    }

    /**
     * 生成 hmacSha1 消息摘要
     * @param source 明文
     * @param securityKey 秘钥
     * @param encoding 字符集
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String hmacSha1(String source, String securityKey , String encoding) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        Assert.notNull(source , "digest source can't be null");
        Assert.notNull(source , "digest encoding can't be null");
        return digestByMac(source.getBytes(encoding) , securityKey.getBytes(encoding) , ALGORITHM_HMAC_SHA1);
    }

    /**
     * 生成 hmacSha224 消息摘要
     * @param source 明文
     * @param securityKey 摘要算法
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String hmacSha224(String source , String securityKey) throws NoSuchAlgorithmException , UnsupportedEncodingException , InvalidKeyException{
        return hmacSha224(source , securityKey , DEFAULT_ENCODING);
    }

    /**
     * 生成 hmacSha224 消息摘要
     * @param source 明文
     * @param securityKey 秘钥
     * @param encoding 字符集
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String hmacSha224(String source, String securityKey , String encoding) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        Assert.notNull(source , "digest source can't be null");
        Assert.notNull(source , "digest encoding can't be null");
        return digestByMac(source.getBytes(encoding) , securityKey.getBytes(encoding) , ALGORITHM_HMAC_SHA224);
    }

    /**
     * 生成 hmacSha256 消息摘要
     * @param source 明文
     * @param securityKey 摘要算法
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String hmacSha256(String source , String securityKey) throws NoSuchAlgorithmException , UnsupportedEncodingException , InvalidKeyException{
        return hmacSha256(source , securityKey , DEFAULT_ENCODING);
    }

    /**
     * 生成 hmacSha256 消息摘要
     * @param source 明文
     * @param securityKey 秘钥
     * @param encoding 字符集
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String hmacSha256(String source, String securityKey , String encoding) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        Assert.notNull(source , "digest source can't be null");
        Assert.notNull(source , "digest encoding can't be null");
        return digestByMac(source.getBytes(encoding) , securityKey.getBytes(encoding) , ALGORITHM_HMAC_SHA256);
    }

    /**
     * 生成 hmacSha384 消息摘要
     * @param source 明文
     * @param securityKey 摘要算法
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String hmacSha384(String source , String securityKey) throws NoSuchAlgorithmException , UnsupportedEncodingException , InvalidKeyException{
        return hmacSha384(source , securityKey , DEFAULT_ENCODING);
    }

    /**
     * 生成 hmacSha384 消息摘要
     * @param source 明文
     * @param securityKey 秘钥
     * @param encoding 字符集
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String hmacSha384(String source, String securityKey , String encoding) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        Assert.notNull(source , "digest source can't be null");
        Assert.notNull(source , "digest encoding can't be null");
        return digestByMac(source.getBytes(encoding) , securityKey.getBytes(encoding) , ALGORITHM_HMAC_SHA384);
    }

    /**
     * 生成 hmacSha512 消息摘要
     * @param source 明文
     * @param securityKey 摘要算法
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String hmacSha512(String source , String securityKey) throws NoSuchAlgorithmException , UnsupportedEncodingException , InvalidKeyException{
        return hmacSha512(source , securityKey , DEFAULT_ENCODING);
    }

    /**
     * 生成 hmacSha512 消息摘要
     * @param source 明文
     * @param securityKey 秘钥
     * @param encoding 字符集
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String hmacSha512(String source, String securityKey , String encoding) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        Assert.notNull(source , "digest source can't be null");
        Assert.notNull(source , "digest encoding can't be null");
        return digestByMac(source.getBytes(encoding) , securityKey.getBytes(encoding) , ALGORITHM_HMAC_SHA512);
    }

    /**
     * 生成 hmac 消息摘要
     * @param source 明文
     * @param securityKey 秘钥
     * @param algorithm 算法
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    private static String digestByMac(byte[] source , byte[] securityKey , String algorithm) throws NoSuchAlgorithmException, InvalidKeyException {
        Assert.notNull(source , "digestByMac source can't be null");
        Assert.notNull(source , "digestByMac securityKey can't be null");
        Assert.notNull(algorithm , "digestByMac algorithm can't be null");
        SecretKeySpec secretKeySpec = new SecretKeySpec(securityKey , algorithm);
        Mac mac = Mac.getInstance(algorithm);
        mac.init(secretKeySpec);
        byte[] result = mac.doFinal(source);
        return ByteUtil.encodeHex(result);
    }
}
