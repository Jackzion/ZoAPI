package com.zoapi.zoapiclientsdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

public class SignUtils {
    public static String genSign(String body , String Secret){
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        String content = body + "." + Secret;
        return md5.digestHex(content);
    }
}
