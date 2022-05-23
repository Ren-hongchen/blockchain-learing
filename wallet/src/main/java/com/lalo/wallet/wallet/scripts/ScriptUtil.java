package com.lalo.wallet.wallet.scripts;

import com.lalo.wallet.wallet.algorithm.ECDSA;

public class ScriptUtil {
    public static String getScriptSig(String private_key, String public_key, String message) throws Exception {
        String signature = ECDSA.sign(message, private_key);
        String signature_length = Integer.toHexString(signature.length());
        String publicKey_length = Integer.toHexString(public_key.length());

        return signature_length + signature + publicKey_length + public_key;
    }
}
