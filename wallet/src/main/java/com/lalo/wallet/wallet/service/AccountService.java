package com.lalo.wallet.wallet.service;

import com.lalo.wallet.wallet.algorithm.Base58;
import com.lalo.wallet.wallet.algorithm.ECDSA;
import com.lalo.wallet.wallet.algorithm.Hash160;
import com.lalo.wallet.wallet.algorithm.Hash256;
import com.lalo.wallet.wallet.dto.OutputDTO;
import com.lalo.wallet.wallet.dto.TransactionDTO;
import com.lalo.wallet.wallet.globalvariable.GlobalVariable;
import com.lalo.wallet.wallet.network.UTXO;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;

@Service
public class AccountService {
    private String private_key = null;
    private String public_key = null;

    public void setKeyPair() throws Exception {
        KeyPair keyPair = ECDSA.getKeyPair();
        ECPublicKey ecPublicKey = (ECPublicKey) keyPair.getPublic();
        ECPrivateKey ecPrivateKey = (ECPrivateKey) keyPair.getPrivate();
        private_key = ecPrivateKey.getS().toString(16);
        String key = ecPublicKey.getW().getAffineY().toString(16);
        if(key.endsWith("2")) {
            public_key = "02" + ecPublicKey.getW().getAffineX().toString(16);
        } else {
            public_key = "03" + ecPublicKey.getW().getAffineX().toString(16);
        }
    }

    public String getPublic_key() {
        return public_key;
    }

    public String getPrivate_key() {
        return private_key;
    }

    public String getWIF() throws Exception{
        return getWIFwithCompress(private_key); //use WIF with Compress in default
    }

    private String getWIFwithCompress(String private_key) throws Exception {
        String s = "80" + private_key + "01";
        String checksum = Hash256.hash256(Hex.decode(s)).substring(0,8);
        return Base58.encode(Hex.decode(s+ checksum));
    }

    private String getWIFwithNoCompress(String private_key) throws Exception {
        String s = "80" + private_key;
        String checksum = Hash256.hash256(Hex.decode(s)).substring(0,8);
        return Base58.encode(Hex.decode(s + checksum));
    }

    public String getAddress() throws Exception {
        String hash160 = Hash160.hash160(Hex.decode(public_key));
        String hash160withPrefix = "00" + hash160;
        String checksum = Hash256.hash256(Hex.decode(hash160withPrefix)).substring(0,8);
        String finalData = hash160withPrefix + checksum;
        return Base58.encode(Hex.decode(finalData));
    }

    public void ScanTx(TransactionDTO transactionDTO) {
        for(OutputDTO outputDTO : transactionDTO.getOutput()) {
            if(outputDTO.getScript().equals(GlobalVariable.scriptPubKey)) {
                UTXO utxo = UTXO.newBuilder().build();

            }
        }
    }
}


