package com.saul.blockchainlearning.model;


import lombok.Data;

@Data
// value: 'C'+ 32-byte transaction hash + output index length + output index
public class Transaction {
    private double amount;
    private boolean isCoinbase;
    private int blockHeight;
    private String lockScript;
}
