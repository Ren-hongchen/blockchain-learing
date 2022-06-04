package com.lalo.wallet.wallet.dto;

import lombok.Data;

@Data
public class ScriptPubKey {
    private String asm;
    private String hex;
    private int reqSigs;
    private String type;
    private String address;
}
