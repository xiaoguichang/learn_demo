package com.xiaogch.common.util.security.aes;

import com.xiaogch.common.util.ByteUtil;
import com.xiaogch.common.util.StringFormat;
import com.xiaogch.common.util.security.CipherMode;
import com.xiaogch.common.util.security.CipherPadding;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;

public class AESUtil {

    private static Provider provider = new BouncyCastleProvider();

    /**
     *
     * @param plainText
     * @param secretKey
     * @param aes
     * @return
     */
    public static byte[] encrypt(byte[] plainText , byte[] secretKey  , byte[] iv , AES aes)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
        String algorithm = getAlgorithm(aes.mode , aes.padding);
        boolean needBouncyCastle = needBouncyCastle(aes.padding);
        Cipher cipher = needBouncyCastle ? Cipher.getInstance(algorithm , provider) : Cipher.getInstance(algorithm);
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey , "AES");
        if (!needIV(aes)) {
            cipher.init(Cipher.ENCRYPT_MODE , secretKeySpec);
        } else {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE , secretKeySpec , ivParameterSpec);
        }
        return cipher.doFinal(plainText);
    }

    /**
     *
     * @param plainText
     * @param secretKey
     * @param aes
     * @return
     */
    public static byte[] encrypt(byte[] plainText , byte[] secretKey  , AES aes)
            throws NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        return encrypt(plainText , secretKey , null ,aes);
    }

    /**
     *
     * @param encryptText
     * @param secretKey
     * @param aes
     * @return
     */
    public static byte[] decrypt(byte[] encryptText , byte[] secretKey , byte[] iv , AES aes)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
        String algorithm = getAlgorithm(aes.mode , aes.padding);
        boolean needBouncyCastle = needBouncyCastle(aes.padding);
        Cipher cipher = needBouncyCastle ? Cipher.getInstance(algorithm , provider) :
                Cipher.getInstance(algorithm);

        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey , "AES");

        if (!needIV(aes)) {
            cipher.init(Cipher.DECRYPT_MODE , secretKeySpec);
        } else {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE , secretKeySpec , ivParameterSpec);
        }
        return cipher.doFinal(encryptText);
    }


    /**
     *
     * @param encryptText
     * @param secretKey
     * @param aes
     * @return
     */
    public static byte[] decrypt(byte[] encryptText , byte[] secretKey , AES aes)
            throws NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        return decrypt(encryptText , secretKey , null , aes);
    }

    /**
     * 加密字符串
     * @param plainText 明文串
     * @param plainTextFormat 明文串格式
     * @param secretKey 秘钥
     * @param secretKeyFormat 秘钥格式
     * @param aes 加密算法
     * @param resultFormat 结果字符串格式
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     */
    public static String encrypt(String plainText , StringFormat plainTextFormat ,
                                 String secretKey , StringFormat secretKeyFormat ,
                                 AES aes , StringFormat resultFormat)
            throws UnsupportedEncodingException , NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {

        byte[] plainTextBytes = ByteUtil.stringToBytes(plainText , plainTextFormat);
        byte[] secretKeyByte = ByteUtil.stringToBytes(secretKey , secretKeyFormat);
        byte[] result = encrypt(plainTextBytes , secretKeyByte , aes);
        return ByteUtil.bytesToString(result , resultFormat);

    }

    /**
     * 加密字符串
     * @param plainText 明文串
     * @param plainTextFormat 明文串格式
     * @param secretKey 秘钥
     * @param secretKeyFormat 秘钥格式
     * @param iv 向量
     * @param ivFormat 向量格式
     * @param aes 加密算法
     * @param resultFormat 结果字符串格式
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     */
    public static String encrypt(String plainText , StringFormat plainTextFormat ,
                                 String secretKey , StringFormat secretKeyFormat ,
                                 String iv , StringFormat ivFormat ,
                                 AES aes , StringFormat resultFormat) throws UnsupportedEncodingException
            , NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException
            , BadPaddingException, InvalidAlgorithmParameterException {
        byte[] plainTextBytes = ByteUtil.stringToBytes(plainText , plainTextFormat);
        byte[] secretKeyBytes = ByteUtil.stringToBytes(secretKey , secretKeyFormat);
        byte[] ivBytes = ByteUtil.stringToBytes(iv , ivFormat);
        byte[] result = encrypt(plainTextBytes , secretKeyBytes ,  ivBytes , aes);
        return ByteUtil.bytesToString(result , resultFormat);
    }


    /**
     * 解密字符串
     * @param encryptText 密文串
     * @param plainTextFormat 密文串格式
     * @param secretKey 秘钥
     * @param secretKeyFormat 秘钥格式
     * @param iv 向量
     * @param ivFormat 向量格式
     * @param aes 加密算法
     * @param resultFormat 结果字符串格式
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     */
    public static String decrypt(String encryptText , StringFormat plainTextFormat ,
                                 String secretKey , StringFormat secretKeyFormat ,
                                 String iv , StringFormat ivFormat ,
                                 AES aes , StringFormat resultFormat)
            throws UnsupportedEncodingException , NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        byte[] plainTextBytes = ByteUtil.stringToBytes(encryptText , plainTextFormat);
        byte[] secretKeyBytes = ByteUtil.stringToBytes(secretKey , secretKeyFormat);
        byte[] ivBytes = ByteUtil.stringToBytes(iv , ivFormat);
        byte[] result = decrypt(plainTextBytes , secretKeyBytes , ivBytes , aes);
        return ByteUtil.bytesToString(result , resultFormat);
    }




    /**
     * 解密字符串
     * @param encryptText 密文串
     * @param plainTextFormat 密文串格式
     * @param secretKey 秘钥
     * @param secretKeyFormat 秘钥格式
     * @param aes 加密算法
     * @param resultFormat 结果字符串格式
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     */
    public static String decrypt(String encryptText , StringFormat plainTextFormat ,
                                 String secretKey , StringFormat secretKeyFormat ,
                                 AES aes , StringFormat resultFormat)
            throws UnsupportedEncodingException , NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        byte[] plainTextBytes = ByteUtil.stringToBytes(encryptText , plainTextFormat);
        byte[] secretKeyByte = ByteUtil.stringToBytes(secretKey , secretKeyFormat);
        byte[] result = decrypt(plainTextBytes , secretKeyByte , aes);
        return ByteUtil.bytesToString(result , resultFormat);
    }


    public static boolean needBouncyCastle(CipherPadding padding) {
        if (padding != null) {
            switch (padding) {
                case PKCS7_PADDING:
                    return true;
                case ZERO_BYTE_PADDING:
                    return true;
                case ISO_10126_2_Padding:
                    return true;
                case X_9_23_PADDING:
                    return true;
                case ISO_7816_4_PADDING:
                    return true;
                case TBC_PADDING:
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }

    private static boolean needIV(AES aes) {
        if (aes != null) {
            switch (aes) {
                case AES:
                    return false;
                case AES_ECB_ISO_10126_PADDING:
                    return false;
                case AES_ECB_PKCS5_PADDING:
                    return false;
                case AES_ECB_PKCS7_PADDING:
                    return false;
                case AES_ECB_ZERO_BYTE_PADDING:
                    return false;
                case AES_ECB_ISO_10126_2_Padding:
                    return false;
                case AES_ECB_X_9_23_PADDING:
                    return false;
                case AES_ECB_ISO_7816_4_PADDING:
                    return false;
                case AES_ECB_TBC_PADDING:
                    return false;
                default:
                    return true;
            }
        }
        return true;

    }

    /**
     * 获取加密算法
     * @param mode 工作模式
     * @param padding 填充方式
     * @return
     */
    public static String getAlgorithm(CipherMode mode, CipherPadding padding){
        StringBuilder sb = new StringBuilder("AES");
        if (mode == null) {
            sb.append("/");
        } else {
            sb.append("/").append(mode.code);
        }

        if (padding == null) {
            sb.append("/");
        } else {
            sb.append("/").append(padding.code);
        }
        return sb.toString();
    }

}
