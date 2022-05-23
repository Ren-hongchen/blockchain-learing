package com.lalo.wallet.wallet.algorithm;

import org.bouncycastle.util.encoders.Hex;

import java.security.MessageDigest;

public class Hash256 {

    public static byte[] SHA256(byte[] data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data);
        return md.digest();
    }

    public static String hash256(byte[] data) throws Exception {
        byte[] mid = SHA256(data);
        return new String(Hex.encode(SHA256(mid)));
    }

}
