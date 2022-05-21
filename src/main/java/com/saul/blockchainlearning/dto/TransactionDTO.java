package com.saul.blockchainlearning.dto;

import lombok.Data;

import java.util.List;

@Data
public class TransactionDTO {
    private int version; // 4 bytes
    private String flag; //Optional flag, if present, must be 0001, which indicates there is witness data in this transaction
    private int inputCounter;
    private List<InputDTO> input;
    private int outputCounter;
    private List<OutputDTO> output;
    //witness list; // Optional list of all witnesses only if Flag is present,
                    // which are used in transaction validation according to specifications for Segregated Witness.

    private int locktime;  //4 bytes; //If non-zero and sequence numbers are < ffffffff:
                                       // it represents either the block height or timestamp when transaction is final.
}
