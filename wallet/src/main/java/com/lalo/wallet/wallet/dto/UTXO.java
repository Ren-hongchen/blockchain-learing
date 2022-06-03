package com.lalo.wallet.wallet.dto;

import lombok.Data;

@Data
public class UTXO {
    private String txid;
    private int vout;
    private String address;
    private String scriptPubKey;
    private double amount;
    private int confirmations;
    private boolean spendable;
    private boolean solvable;
}
