package com.saul.blockchainlearning.algorithm;


import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import org.bouncycastle.asn1.sec.SECNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.ECPointUtil;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.encoders.Hex;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;

public class ECDSA {

    public static String getPublicKey(String private_key) {
        BigInteger privKey = new BigInteger(private_key,16);
        X9ECParameters ecp = SECNamedCurves.getByName("secp256k1");
        ECPoint curvePt = ecp.getG().multiply(privKey);
        ECDomainParameters ecDomainParameters = new ECDomainParameters(ecp);
        ECPublicKeyParameters publicKeyParameters = new ECPublicKeyParameters(curvePt,ecDomainParameters);
        return new String(Hex.encode(publicKeyParameters.getQ().getEncoded(true))); //true为压缩 false为非压缩
    }

    public static String sign(String message, String private_key) throws Exception {
        Sign sign = SecureUtil.sign(SignAlgorithm.SHA256withECDSA, Hex.decode(private_key), null);
        byte[] signed = sign.sign(message.getBytes());
        return new String(Hex.encode(signed));
    }

    public static boolean verify(String message, String signature, String public_key) throws Exception {
        Sign sign = SecureUtil.sign(SignAlgorithm.SHA256withECDSA, null, Hex.decode(public_key));
        return sign.verify(message.getBytes(), Hex.decode(signature));
    }
}
