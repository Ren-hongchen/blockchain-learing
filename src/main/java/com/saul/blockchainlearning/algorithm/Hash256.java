package com.saul.blockchainlearning.algorithm;

import org.bouncycastle.util.encoders.Hex;

import java.security.MessageDigest;

public class Hash256 {

    private static String SHA256(byte[] data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data);
        return new String(Hex.encode(md.digest()));
    }

    public static String hash256(byte[] data) throws Exception {
        String mid = SHA256(data);
        return SHA256(mid.getBytes());
    }

}
