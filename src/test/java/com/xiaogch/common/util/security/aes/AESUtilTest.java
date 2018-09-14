package com.xiaogch.common.util.security.aes;

import com.xiaogch.common.util.ByteUtil;
import com.xiaogch.common.util.StringFormat;
import com.xiaogch.common.util.security.CipherMode;
import com.xiaogch.common.util.security.CipherPadding;
import com.xiaogch.common.util.security.SecretKeyUtil;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/9/14 18:06 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class AESUtilTest {

    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String sourceText = "hello , 我是肖贵长";
        String secretKey = SecretKeyUtil.getAESSecretKey256(StringFormat.HEX);
        System.out.println("secretKey=" + secretKey);
        System.out.println("text=" + sourceText);

        System.out.println("=======================================");


        byte[] plainText = sourceText.getBytes("utf-8");
        byte[] secretKeyByte = ByteUtil.decodeHex(secretKey);
        String ivStr = SecretKeyUtil.getIV(StringFormat.HEX);
        byte[] iv = ivStr.getBytes("utf-8");

        for (AES aes : AES.values()) {
            try {
                byte[] encryptBytes = AESUtil.encrypt(plainText , secretKeyByte , aes);
                byte[] decryptBytes = AESUtil.decrypt(encryptBytes , secretKeyByte , aes);
                String newStr = new String(decryptBytes , "utf-8");
                if (sourceText.equals(newStr)) {
                    System.out.println("" + aes);
                }
            } catch (Exception e) {
            }
        }


        System.out.println("===================================================");
        System.out.println("===================================================");
        System.out.println("===================================================");

        for (AES aes : AES.values()) {

            try {
                byte[] encryptBytes = AESUtil.encrypt(plainText , secretKeyByte , iv , aes);
                byte[] decryptBytes = AESUtil.decrypt(encryptBytes , secretKeyByte , iv , aes);
                String newStr = new String(decryptBytes , "utf-8");
                if (sourceText.equals(newStr)) {
                    System.out.println("" + aes);
                }
            } catch (Exception e) {

            }
        }


        Provider provider = new BouncyCastleProvider();
        CipherPadding[] cipherPaddings = CipherPadding.values();
        CipherMode[] cipherModes = CipherMode.values();
        for (CipherMode cipherMode : cipherModes) {
            for (CipherPadding cipherPadding : cipherPaddings) {
                String algorithm = AESUtil.getAlgorithm(cipherMode , cipherPadding);
                boolean needBouncyCastle = AESUtil.needBouncyCastle(cipherPadding);
                try {
                    Cipher cipher = needBouncyCastle ? Cipher.getInstance(algorithm , provider) : Cipher.getInstance(algorithm);
                    System.out.println("\t/** cipherMode=" + cipherMode.code + "  cipherPadding=" + cipherPadding.code + " */");
                    System.out.println("\tAES_" + cipherMode + "_" + cipherPadding + "(CipherMode." + cipherMode + " , CipherPadding." + cipherPadding + "),");
                    System.out.println();
                } catch (NoSuchAlgorithmException e) {
                    System.out.println("#####" + e.getMessage());
                } catch (NoSuchPaddingException e) {
                    System.out.println("@@@@@" + e.getMessage());
                    System.out.println("@@@@@\t" + cipherMode + "\t" + cipherPadding);
                }
            }
        }


        String textStr = "vqs6qY5XjIw7rFRzSnWhCxrfumQocu/CEYpTpCM/kBdHJrUAJOJDp+s8+Cg+qlY8TZvh8DCkua4Z/Y2of97JtdcrHzhjsAyQLN9j/gK12bNaGzMEezWFCjKDmvepEHeGHe6z91p8za7pBe6hdGWWDCEV1ehTmHhlm49gx2tBSKQsqjMI5svYZfZ/YHmGeaf+kRjXbjG0htkX8BV82GCGi71/pvs9hPxS3LoAVCzf0pfiH8CB6TxWPKKyfBe2jf4x+TD91DxujgcWqt9g8lyU9BZOQe2eJ5cYBQ5OI+9Lpi5GyS92QdGfzwCSFZZWwYrlM41oBYSNix1++WJtYQcd/+7d1YcLOXTYr6VNtACHs2fXRdkuoxTqIVrmhx2OCmlf4qzJjnnhtYgUj0TO1T2e9Zsk54PXVhURyjUPnMvXQDamUAxgPwtoHGrygA2gmbo8TqO+KPQQQFGfVcUIU2sgPQOEo8f++9otTzHZkdr89i977V47WjlSpfSV1saAlgsNLuq04EpI9407C/gVB3C7bA==";
        String keyStr = "Y34cJV95hehy03cR2L1DLg==";
        String iv_str = "5ul57USzvx4W5NBy2VzbYA==";


        try {
            String result = AESUtil.decrypt(textStr , StringFormat.BASE64 ,
                    keyStr , StringFormat.BASE64 ,
                    iv_str ,StringFormat.BASE64 ,
                    AES.AES_CBC_PKCS7_PADDING , StringFormat.COMMON_STRING);
            System.out.println(result);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }

}