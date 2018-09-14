package com.xiaogch.common.util.security;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/9/13 18:12 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public enum CipherPadding {

    NONE(""),

    /** jdk: NoPadding */
    NO_PADDING("NoPadding") ,

    /** jdk: ISO10126Padding */
    ISO_10126_PADDING("ISO10126Padding"),

    /** jdk: OAEPPadding */
    OAEP_PADDING("OAEPPadding"),

    /** jdk: PKCS1Padding */
    PKCS1_PADDING("PKCS1Padding"),

    /** jdk: PKCS5Padding */
    PKCS5_PADDING("PKCS5Padding"),

    /** jdk: SSL3Padding */
    SSL3_PADDING("SSL3Padding"),

    /** bouncy castle : PKCS7Padding */
    PKCS7_PADDING("PKCS7Padding"),

    /** bouncy castle : ZeroBytePadding */
    ZERO_BYTE_PADDING("ZeroBytePadding"),

    /** bouncy castle : ISO10126-2Padding */
    ISO_10126_2_Padding("ISO10126-2Padding"),

    /** bouncy castle : X9.23Padding */
    X_9_23_PADDING("X9.23Padding"),

    /** bouncy castle : ISO7816-4Padding */
    ISO_7816_4_PADDING("ISO7816-4Padding"),

    /** bouncy castle : TBCPadding */
    TBC_PADDING("TBCPadding");

    public final String code;

    CipherPadding(String code) {
        this.code = code;
    }
}
