package com.lalo.wallet.wallet.algorithm;


import org.bouncycastle.asn1.x9.ECNamedCurveTable;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.jce.ECPointUtil;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.bouncycastle.util.encoders.Hex;

import java.math.BigInteger;
import java.security.*;
import java.security.spec.*;

public class ECDSA {

    public static KeyPair getKeyPair() {
        try {
            Security.addProvider(new BouncyCastleProvider());
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
            keyPairGenerator.initialize(new ECGenParameterSpec("secp256k1"));
            return keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            System.out.println(e.getCause().getMessage());
        }
        return null;
    }

    public static PrivateKey getPrivateKeyFromECBigIntAndCurve(BigInteger s, String curveName) {
        X9ECParameters ecCurve = ECNamedCurveTable.getByName(curveName);
        ECParameterSpec ecParameterSpec = new ECNamedCurveSpec(curveName, ecCurve.getCurve(), ecCurve.getG(), ecCurve.getN(), ecCurve.getH(), ecCurve.getSeed());
        ECPrivateKeySpec privateKeySpec = new ECPrivateKeySpec(s, ecParameterSpec);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            return keyFactory.generatePrivate(privateKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static PublicKey getPublicKeyFromStringAndCurve(String s, String curveName) {
        X9ECParameters ecCurve = ECNamedCurveTable.getByName(curveName);
        ECParameterSpec ecParameterSpec = new ECNamedCurveSpec(curveName, ecCurve.getCurve(), ecCurve.getG(), ecCurve.getN());
        ECPoint point = ECPointUtil.decodePoint(ecParameterSpec.getCurve(), Hex.decode(s));
        System.out.println(point.getAffineX().toString(16));
        ECPublicKeySpec publicKeySpec = new ECPublicKeySpec(point,ecParameterSpec);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            return keyFactory.generatePublic(publicKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String sign(String message, String private_key) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        BigInteger s = new BigInteger(private_key,16);
        PrivateKey privateKey = getPrivateKeyFromECBigIntAndCurve(s,"secp256k1");
        Signature signature = Signature.getInstance("SHA256withECDSA","BC");
        signature.initSign(privateKey);
        signature.update(message.getBytes());
        return new String(Hex.encode(signature.sign()));
    }

    public static boolean verify(String message, String signature, String public_key) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        PublicKey publicKey = getPublicKeyFromStringAndCurve(public_key, "secp256k1");
        Signature signature1 = Signature.getInstance("SHA256withECDSA","BC");
        signature1.initVerify(publicKey);
        signature1.update(message.getBytes());
        return signature1.verify(Hex.decode(signature));
    }
}
