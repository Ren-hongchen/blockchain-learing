package com.saul.blockchainlearning.algorithm;


import org.bouncycastle.asn1.x9.ECNamedCurveTable;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.bouncycastle.util.encoders.Hex;

import java.math.BigInteger;
import java.security.*;
import java.security.spec.*;

public class ECDSA {

    public static KeyPair getKeyPair() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
        keyPairGenerator.initialize(new ECGenParameterSpec("secp256k1"));
        return keyPairGenerator.generateKeyPair();
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

    public static PublicKey getPublicKeyFromECBigIntAndCurve(BigInteger s, String curveName) {
        X9ECParameters ecCurve = ECNamedCurveTable.getByName(curveName);
        ECParameterSpec ecParameterSpec = new ECNamedCurveSpec(curveName, ecCurve.getCurve(), ecCurve.getG(), ecCurve.getN(), ecCurve.getH(), ecCurve.getSeed());
        ECPublicKeySpec publicKeySpec = new ECPublicKeySpec(ecParameterSpec.getGenerator(),ecParameterSpec);
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
        System.out.println(private_key.toString());
        Signature signature = Signature.getInstance("SHA256withECDSA","BC");
        signature.initSign(privateKey);
        signature.update(message.getBytes());
        return new String(Hex.encode(signature.sign()));
    }

    public static boolean verify(String message, String signature, String public_key) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        BigInteger s = new BigInteger(public_key, 16);
        PublicKey publicKey = getPublicKeyFromECBigIntAndCurve(s, "secp256k1");
        System.out.println(public_key.toString());
        Signature signature1 = Signature.getInstance("SHA256withECDSA","BC");
        signature1.initVerify(publicKey);
        signature1.update(message.getBytes());
        return signature1.verify(Hex.decode(signature));
    }
}
