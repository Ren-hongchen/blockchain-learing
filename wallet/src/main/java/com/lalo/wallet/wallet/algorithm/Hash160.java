package com.lalo.wallet.wallet.algorithm;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.security.MessageDigest;
import java.security.Security;

public class Hash160 {

    private static byte[] RipeMD160(byte[] data) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        MessageDigest md = MessageDigest.getInstance("RipeMD160");
        return md.digest(data);
    }

    private static byte[] SHA256(byte[] data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data);
        return md.digest();
    }

    public static String hash160(byte[] data) throws Exception {
        byte[] mid = SHA256(data);
        return new String(Hex.encode(RipeMD160(mid)));
    }
}
