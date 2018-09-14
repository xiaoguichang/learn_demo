package com.xiaogch.common.util.security.aes;

import com.xiaogch.common.util.security.CipherMode;
import com.xiaogch.common.util.security.CipherPadding;

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
public enum AES {

    /** default cipherMode=NONE , cipherPadding=NONE */
    AES(CipherMode.NONE , CipherPadding.NONE),

    /** cipherMode=CBC  cipherPadding=NoPadding */
    AES_CBC_NO_PADDING(CipherMode.CBC , CipherPadding.NO_PADDING),

    /** cipherMode=CBC  cipherPadding=ISO10126Padding */
    AES_CBC_ISO_10126_PADDING(CipherMode.CBC , CipherPadding.ISO_10126_PADDING),

    /** cipherMode=CBC  cipherPadding=PKCS5Padding */
    AES_CBC_PKCS5_PADDING(CipherMode.CBC , CipherPadding.PKCS5_PADDING),

    /** cipherMode=CBC  cipherPadding=PKCS7Padding */
    AES_CBC_PKCS7_PADDING(CipherMode.CBC , CipherPadding.PKCS7_PADDING),

    /** cipherMode=CBC  cipherPadding=ZeroBytePadding */
    AES_CBC_ZERO_BYTE_PADDING(CipherMode.CBC , CipherPadding.ZERO_BYTE_PADDING),

    /** cipherMode=CBC  cipherPadding=ISO10126-2Padding */
    AES_CBC_ISO_10126_2_Padding(CipherMode.CBC , CipherPadding.ISO_10126_2_Padding),

    /** cipherMode=CBC  cipherPadding=X9.23Padding */
    AES_CBC_X_9_23_PADDING(CipherMode.CBC , CipherPadding.X_9_23_PADDING),

    /** cipherMode=CBC  cipherPadding=ISO7816-4Padding */
    AES_CBC_ISO_7816_4_PADDING(CipherMode.CBC , CipherPadding.ISO_7816_4_PADDING),

    /** cipherMode=CBC  cipherPadding=TBCPadding */
    AES_CBC_TBC_PADDING(CipherMode.CBC , CipherPadding.TBC_PADDING),

    /** cipherMode=CFB  cipherPadding=NoPadding */
    AES_CFB_NO_PADDING(CipherMode.CFB , CipherPadding.NO_PADDING),

    /** cipherMode=CFB  cipherPadding=ISO10126Padding */
    AES_CFB_ISO_10126_PADDING(CipherMode.CFB , CipherPadding.ISO_10126_PADDING),

    /** cipherMode=CFB  cipherPadding=PKCS5Padding */
    AES_CFB_PKCS5_PADDING(CipherMode.CFB , CipherPadding.PKCS5_PADDING),

    /** cipherMode=CFB  cipherPadding=PKCS7Padding */
    AES_CFB_PKCS7_PADDING(CipherMode.CFB , CipherPadding.PKCS7_PADDING),

    /** cipherMode=CFB  cipherPadding=ZeroBytePadding */
    AES_CFB_ZERO_BYTE_PADDING(CipherMode.CFB , CipherPadding.ZERO_BYTE_PADDING),

    /** cipherMode=CFB  cipherPadding=ISO10126-2Padding */
    AES_CFB_ISO_10126_2_Padding(CipherMode.CFB , CipherPadding.ISO_10126_2_Padding),

    /** cipherMode=CFB  cipherPadding=X9.23Padding */
    AES_CFB_X_9_23_PADDING(CipherMode.CFB , CipherPadding.X_9_23_PADDING),

    /** cipherMode=CFB  cipherPadding=ISO7816-4Padding */
    AES_CFB_ISO_7816_4_PADDING(CipherMode.CFB , CipherPadding.ISO_7816_4_PADDING),

    /** cipherMode=CFB  cipherPadding=TBCPadding */
    AES_CFB_TBC_PADDING(CipherMode.CFB , CipherPadding.TBC_PADDING),

    /** cipherMode=CTR  cipherPadding=NoPadding */
    AES_CTR_NO_PADDING(CipherMode.CTR , CipherPadding.NO_PADDING),

    /** cipherMode=CTR  cipherPadding=ISO10126Padding */
    AES_CTR_ISO_10126_PADDING(CipherMode.CTR , CipherPadding.ISO_10126_PADDING),

    /** cipherMode=CTR  cipherPadding=PKCS5Padding */
    AES_CTR_PKCS5_PADDING(CipherMode.CTR , CipherPadding.PKCS5_PADDING),

    /** cipherMode=CTR  cipherPadding=PKCS7Padding */
    AES_CTR_PKCS7_PADDING(CipherMode.CTR , CipherPadding.PKCS7_PADDING),

    /** cipherMode=CTR  cipherPadding=ZeroBytePadding */
    AES_CTR_ZERO_BYTE_PADDING(CipherMode.CTR , CipherPadding.ZERO_BYTE_PADDING),

    /** cipherMode=CTR  cipherPadding=ISO10126-2Padding */
    AES_CTR_ISO_10126_2_Padding(CipherMode.CTR , CipherPadding.ISO_10126_2_Padding),

    /** cipherMode=CTR  cipherPadding=X9.23Padding */
    AES_CTR_X_9_23_PADDING(CipherMode.CTR , CipherPadding.X_9_23_PADDING),

    /** cipherMode=CTR  cipherPadding=ISO7816-4Padding */
    AES_CTR_ISO_7816_4_PADDING(CipherMode.CTR , CipherPadding.ISO_7816_4_PADDING),

    /** cipherMode=CTR  cipherPadding=TBCPadding */
    AES_CTR_TBC_PADDING(CipherMode.CTR , CipherPadding.TBC_PADDING),

    /** cipherMode=CTS  cipherPadding=NoPadding */
    AES_CTS_NO_PADDING(CipherMode.CTS , CipherPadding.NO_PADDING),

    /** cipherMode=CTS  cipherPadding=ISO10126Padding */
    AES_CTS_ISO_10126_PADDING(CipherMode.CTS , CipherPadding.ISO_10126_PADDING),

    /** cipherMode=CTS  cipherPadding=PKCS5Padding */
    AES_CTS_PKCS5_PADDING(CipherMode.CTS , CipherPadding.PKCS5_PADDING),

    /** cipherMode=CTS  cipherPadding=PKCS7Padding */
    AES_CTS_PKCS7_PADDING(CipherMode.CTS , CipherPadding.PKCS7_PADDING),

    /** cipherMode=CTS  cipherPadding=ZeroBytePadding */
    AES_CTS_ZERO_BYTE_PADDING(CipherMode.CTS , CipherPadding.ZERO_BYTE_PADDING),

    /** cipherMode=CTS  cipherPadding=ISO10126-2Padding */
    AES_CTS_ISO_10126_2_Padding(CipherMode.CTS , CipherPadding.ISO_10126_2_Padding),

    /** cipherMode=CTS  cipherPadding=X9.23Padding */
    AES_CTS_X_9_23_PADDING(CipherMode.CTS , CipherPadding.X_9_23_PADDING),

    /** cipherMode=CTS  cipherPadding=ISO7816-4Padding */
    AES_CTS_ISO_7816_4_PADDING(CipherMode.CTS , CipherPadding.ISO_7816_4_PADDING),

    /** cipherMode=CTS  cipherPadding=TBCPadding */
    AES_CTS_TBC_PADDING(CipherMode.CTS , CipherPadding.TBC_PADDING),

    /** cipherMode=ECB  cipherPadding=NoPadding */
    AES_ECB_NO_PADDING(CipherMode.ECB , CipherPadding.NO_PADDING),

    /** cipherMode=ECB  cipherPadding=ISO10126Padding */
    AES_ECB_ISO_10126_PADDING(CipherMode.ECB , CipherPadding.ISO_10126_PADDING),

    /** cipherMode=ECB  cipherPadding=PKCS5Padding */
    AES_ECB_PKCS5_PADDING(CipherMode.ECB , CipherPadding.PKCS5_PADDING),

    /** cipherMode=ECB  cipherPadding=PKCS7Padding */
    AES_ECB_PKCS7_PADDING(CipherMode.ECB , CipherPadding.PKCS7_PADDING),

    /** cipherMode=ECB  cipherPadding=ZeroBytePadding */
    AES_ECB_ZERO_BYTE_PADDING(CipherMode.ECB , CipherPadding.ZERO_BYTE_PADDING),

    /** cipherMode=ECB  cipherPadding=ISO10126-2Padding */
    AES_ECB_ISO_10126_2_Padding(CipherMode.ECB , CipherPadding.ISO_10126_2_Padding),

    /** cipherMode=ECB  cipherPadding=X9.23Padding */
    AES_ECB_X_9_23_PADDING(CipherMode.ECB , CipherPadding.X_9_23_PADDING),

    /** cipherMode=ECB  cipherPadding=ISO7816-4Padding */
    AES_ECB_ISO_7816_4_PADDING(CipherMode.ECB , CipherPadding.ISO_7816_4_PADDING),

    /** cipherMode=ECB  cipherPadding=TBCPadding */
    AES_ECB_TBC_PADDING(CipherMode.ECB , CipherPadding.TBC_PADDING),

    /** cipherMode=GCM  cipherPadding=NoPadding */
    AES_GCM_NO_PADDING(CipherMode.GCM , CipherPadding.NO_PADDING),

    /** cipherMode=GCM  cipherPadding=ISO10126Padding */
    AES_GCM_ISO_10126_PADDING(CipherMode.GCM , CipherPadding.ISO_10126_PADDING),

    /** cipherMode=GCM  cipherPadding=PKCS5Padding */
    AES_GCM_PKCS5_PADDING(CipherMode.GCM , CipherPadding.PKCS5_PADDING),

    /** cipherMode=OFB  cipherPadding=NoPadding */
    AES_OFB_NO_PADDING(CipherMode.OFB , CipherPadding.NO_PADDING),

    /** cipherMode=OFB  cipherPadding=ISO10126Padding */
    AES_OFB_ISO_10126_PADDING(CipherMode.OFB , CipherPadding.ISO_10126_PADDING),

    /** cipherMode=OFB  cipherPadding=PKCS5Padding */
    AES_OFB_PKCS5_PADDING(CipherMode.OFB , CipherPadding.PKCS5_PADDING),

    /** cipherMode=OFB  cipherPadding=PKCS7Padding */
    AES_OFB_PKCS7_PADDING(CipherMode.OFB , CipherPadding.PKCS7_PADDING),

    /** cipherMode=OFB  cipherPadding=ZeroBytePadding */
    AES_OFB_ZERO_BYTE_PADDING(CipherMode.OFB , CipherPadding.ZERO_BYTE_PADDING),

    /** cipherMode=OFB  cipherPadding=ISO10126-2Padding */
    AES_OFB_ISO_10126_2_Padding(CipherMode.OFB , CipherPadding.ISO_10126_2_Padding),

    /** cipherMode=OFB  cipherPadding=X9.23Padding */
    AES_OFB_X_9_23_PADDING(CipherMode.OFB , CipherPadding.X_9_23_PADDING),

    /** cipherMode=OFB  cipherPadding=ISO7816-4Padding */
    AES_OFB_ISO_7816_4_PADDING(CipherMode.OFB , CipherPadding.ISO_7816_4_PADDING),

    /** cipherMode=OFB  cipherPadding=TBCPadding */
    AES_OFB_TBC_PADDING(CipherMode.OFB , CipherPadding.TBC_PADDING),

    /** cipherMode=PCBC  cipherPadding=NoPadding */
    AES_PCBC_NO_PADDING(CipherMode.PCBC , CipherPadding.NO_PADDING),

    /** cipherMode=PCBC  cipherPadding=ISO10126Padding */
    AES_PCBC_ISO_10126_PADDING(CipherMode.PCBC , CipherPadding.ISO_10126_PADDING),

    /** cipherMode=PCBC  cipherPadding=PKCS5Padding */
    AES_PCBC_PKCS5_PADDING(CipherMode.PCBC , CipherPadding.PKCS5_PADDING);


    public CipherMode mode;
    public CipherPadding padding;

    AES(CipherMode mode, CipherPadding padding) {
        this.mode = mode;
        this.padding = padding;
    }
}
