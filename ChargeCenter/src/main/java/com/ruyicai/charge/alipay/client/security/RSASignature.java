/**
 * Alipay.com Inc.
 * Copyright (c) 2005-2006 All Rights Reserved.
 */
package com.ruyicai.charge.alipay.client.security;

import java.io.ByteArrayInputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.apache.commons.codec.binary.Base64;

//import com.alipay.client.util.KeyReader;

/**
 * 
 * 
 * @author stone.zhangjl
 * @version $Id: RSASignature.java, v 0.1 2008-8-21 下午05:13:23 stone.zhangjl Exp $
 */
public class RSASignature implements Signature {

    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    /** 
     * 签名
     * (non-Javadoc)
     * @see com.alipay.api.security.Signature#sign(java.lang.String, java.lang.String, java.lang.String, com.alipay.api.enums.SignatureStyleEnum)
     */
    public String sign(String content, String privateKey) throws Exception {

        PrivateKey prikey = KeyReader.getPrivateKeyFromPKCS8("RSA", new ByteArrayInputStream(
            privateKey.getBytes()));

        java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
        signature.initSign(prikey);
        signature.update(content.getBytes("utf-8"));

        byte[] signBytes = signature.sign();

        String sign = new String(Base64.encodeBase64(signBytes));

        return sign;
    }

    /** 
     * 验证签名
     * (non-Javadoc)
     * @see com.alipay.api.security.Signature#verify(java.lang.String, java.lang.String, java.lang.String, java.lang.String, com.alipay.api.enums.SignatureStyleEnum)
     */
    public boolean verify(String content, String sign, String publicKey) throws Exception {

        PublicKey pubKey = KeyReader.getPublicKeyFromX509("RSA", new ByteArrayInputStream(publicKey
            .getBytes()));

        byte[] signed = Base64.decodeBase64(sign.getBytes());

        java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
        signature.initVerify(pubKey);
        signature.update(content.getBytes("utf-8"));

        boolean verify = signature.verify(signed);

        return verify;
    }
}
