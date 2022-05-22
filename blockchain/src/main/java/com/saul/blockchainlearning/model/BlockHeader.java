package com.saul.blockchainlearning.model;


import lombok.Data;

@Data
public class BlockHeader {
    private String version;
    private String prevHash;
    private String merkleHash;
    private Long timestamp;
    private Long bits;
    private String nonce;
}
