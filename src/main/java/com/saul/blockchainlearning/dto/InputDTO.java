package com.saul.blockchainlearning.dto;

import lombok.Data;

@Data
public class InputDTO {
    private String prev_txid; //32 bytes
    private String prev_vout; //4 bytes
    private int script_length;
    private String script;
    private String sequence_number; //4 bytes; Used as a relative lock time if transaction version is >= 2.
}
