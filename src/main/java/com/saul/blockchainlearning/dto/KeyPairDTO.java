package com.saul.blockchainlearning.dto;

import lombok.Data;

@Data
public class KeyPairDTO {
    private String private_key;
    private String wif;
    private String public_key;
}
