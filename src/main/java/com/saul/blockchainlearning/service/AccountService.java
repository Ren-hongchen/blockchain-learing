package com.saul.blockchainlearning.service;

import com.saul.blockchainlearning.algorithm.Base58;
import com.saul.blockchainlearning.algorithm.Hash256;
import com.saul.blockchainlearning.utils.HexUtil;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class AccountService {

    public String getPrivateKey(){
        byte[] value = new byte[32];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(value);
        return new String(Hex.encode(value));
    }

    public String getWIF(String private_key) throws Exception{
        return getWIFwithCompress(private_key); //use WIF with Compress in default
    }

    private String getWIFwithCompress(String private_key) throws Exception {
        String data = "80" + private_key + "01";
        String checksum = Hash256.hash256(private_key.getBytes()).substring(0,8);
        return Base58.encode(HexUtil.hexStringToByteArray(data + checksum));
    }

    private String getWIFwithNoCompress() {
        return new String();
    }

}


