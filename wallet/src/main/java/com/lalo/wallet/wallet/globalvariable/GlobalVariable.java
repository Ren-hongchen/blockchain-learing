package com.lalo.wallet.wallet.globalvariable;

import com.lalo.wallet.wallet.algorithm.Hash160;
import com.lalo.wallet.wallet.dto.UTXO;
import lombok.Data;
import org.bouncycastle.util.encoders.Hex;

import java.util.List;

@Data
public class GlobalVariable {
    public static String scriptPubKey;
    public static List<Address> AddressList;
    public static List<UTXO> utxoList;


    public static void resetScriptPubkey(String public_key) throws Exception {
        scriptPubKey = "76a914" + Hash160.hash160(Hex.decode(public_key)) + "88ac";
    }
}
