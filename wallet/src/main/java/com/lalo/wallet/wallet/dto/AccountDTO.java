package com.lalo.wallet.wallet.dto;

import lombok.Data;

@Data
public class AccountDTO {
    private String private_key;
    private String public_key;
    private String wif;
    private String address;
    private double UTXO;
}
