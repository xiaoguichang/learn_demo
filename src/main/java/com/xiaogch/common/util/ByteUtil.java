package com.xiaogch.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.Assert;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

/**
 * Author: guich <BR>
 * Version: v 1.0 <BR>
 * Date: 2017/9/26 16:55 <BR>
 * Description: 基本的字节操作封装 <BR>
 * Function List: <BR>
 */
public class ByteUtil {

    private static final Logger logger = LogManager.getLogger(ByteUtil.class);
    private static final int BYTE_LENGTH_OF_LONG = 8;
    private static final int BYTE_LENGTH_OF_INT = 4;
    private static final int BYTE_LENGTH_OF_SHORT = 2;

    public static byte[] long2Byte(long value) {
        byte[] bytes = new byte[BYTE_LENGTH_OF_LONG];
        for (int index = 0; index < BYTE_LENGTH_OF_LONG ; index ++) {
            int offset = 8 * (BYTE_LENGTH_OF_LONG - index - 1);
            bytes[index] = (byte) ((value >> offset) & 0xFF);
        }
        return bytes;
    }

    public static byte[] int2Bytes(int value) {
        byte[] bytes = new byte[BYTE_LENGTH_OF_INT];
        for (int index = 0; index < BYTE_LENGTH_OF_INT ; index ++) {
            bytes[BYTE_LENGTH_OF_INT - index - 1] = (byte)((value >> 8*index) & 0XFF);
        }
        return bytes;
    }

    public static byte[] short2Byte(short value) {
        byte[] bytes = new byte[BYTE_LENGTH_OF_SHORT];
        for (int index = 0; index < BYTE_LENGTH_OF_SHORT ; index ++) {
            bytes[BYTE_LENGTH_OF_SHORT - index - 1] = (byte)((value >> 8*index) & 0XFF);
        }
        return bytes;
    }

    public static long bytes2Long(byte[] source) {
        Assert.isTrue(source.length <= BYTE_LENGTH_OF_LONG , "source length must be less than " + BYTE_LENGTH_OF_LONG);
        long value = 0;
        for (int index = 0 ; index < source.length ; index ++){
            int offset = 8*(source.length - index - 1);
            value = value | ((long)source[index] & 0Xff) << offset;
        }
        return value;
    }

    public static int bytes2Int(byte[] source) {
        Assert.isTrue(source.length <= BYTE_LENGTH_OF_INT , "source length must be less than " + BYTE_LENGTH_OF_INT);
        int value = 0;
        for (int index = 0 ; index < source.length ; index ++){
            int offset = 8*(source.length - index - 1);
            value = value | ((int)source[index] & 0Xff) << offset;
        }
        return value;
    }

    public static short bytes2Short(byte[] source) {
        Assert.isTrue(source.length <= BYTE_LENGTH_OF_SHORT , "source length must be less than " + BYTE_LENGTH_OF_SHORT);
        short value = 0;
        for (int index = 0 ; index < source.length ; index ++){
            int offset = 8*(source.length - index - 1);
            value = (short) (value | ((short)source[index] & 0Xff) << offset);
        }
        return value;
    }

    /** 十六进制正则表达式表示 */
    private static final Pattern hexPattern = Pattern.compile("[0-9a-fA-F]*");

    /** 十六进制表示字符集 */
    private static final char hexChars[] = { '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    /**
     * 将16进制字符串转换成字节数组
     * @param hexSrc 要转换的16进制字符串
     * @return 返回转换后的字节数组，异常返回空字节数组
     */
    public static byte[] decodeHex(final String hexSrc){
        if (hexSrc == null || !hexPattern.matcher(hexSrc).matches()) {
            return new byte[0];
        }

        String tempHex = hexSrc;
        if (hexSrc.length() % 2 != 0) {
            tempHex = "0" + tempHex;
        }

        byte bts[] = new byte[tempHex.length() / 2];
        for (int index = 0 ; index < tempHex.length() ; index = index + 2) {
            String subStr = tempHex.substring(index, index + 2);
            bts[index/2] = (byte)((int)Integer.valueOf(subStr, 16));
        }
        return bts;
    }

    /***
     * 
     * 将字符串转换成特定编码格式的字节数组
     * @param source 要转换的字符串
     * @param encoding 字符编码，如果为空默认为UTF-8
     * @return 返回转换后的字节数组， 如果要转换的<b>字符串为空</b>或<b>系统不支持相应的字符集</b>返回空字节数组
     */
    public static byte[] stringToBytes(final String source , String encoding) {
        if (source == null) {
            return new byte[0];
        }

        if (encoding == null) {
            encoding = "UTF-8";
        }
        try {
            return source.getBytes(encoding);
        } catch (final UnsupportedEncodingException e) {
            logger.error("system unsupported encoding(" + encoding + ") exception" , e);
        }
        return new byte[0];
    }

    /**
     * 拷贝字节数组
     * @param bytes 要拷贝的字节数组
     * @return 拷贝后的数组
     */
    public static byte[] copyOf(final byte[] bytes) {
        if (bytes == null) {
            return new byte[0];
        }
        final byte[] copy = new byte[bytes.length];
        System.arraycopy(bytes, 0, copy, 0, bytes.length);
        return copy;
    }

    /**
     * 将字节数组转换成16进制字符串
     * @param bytes 要转换的二进制数组
     * @return 转换后的字节数组
     */
    public static String encodeHex(byte bytes[]) {
        if (bytes == null) {
            return new String();
        }
        char chars[] = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int value = bytes[i] & 0xFF;
            chars[2 * i] = hexChars[value >>> 4];
            chars[2 * i + 1] = hexChars[value & 0x0F];
        }
        return new String(chars);
    }
}
