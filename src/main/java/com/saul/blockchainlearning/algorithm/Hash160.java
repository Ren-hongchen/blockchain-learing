package com.saul.blockchainlearning.algorithm;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.security.MessageDigest;
import java.security.Security;

public class Hash160 {

    private static String RipeMD160(byte[] data) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        MessageDigest md = MessageDigest.getInstance("RipeMD160");
        return new String(Hex.encode(md.digest(data)));
    }

    private static String SHA256(byte[] data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data);
        return new String(Hex.encode(md.digest()));
    }

    public static String hash160(byte[] data) throws Exception {
        String mid = SHA256(data);
        return RipeMD160(mid.getBytes());
    }
}
