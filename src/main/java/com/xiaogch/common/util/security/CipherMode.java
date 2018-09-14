package com.xiaogch.common.util.security;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/9/13 18:18 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public enum  CipherMode {

    NONE("") ,

    CBC("CBC"),

    CCM("CCM"),

    CFB("CFB"),

    CTR("CTR"),

    CTS("CTS"),

    ECB("ECB"),

    GCM("GCM"),

    OFB("OFB"),

    PCBC("PCBC");

    public final String code;

    CipherMode(String code) {
        this.code = code;
    }
}
