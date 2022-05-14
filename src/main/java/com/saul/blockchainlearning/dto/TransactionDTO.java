package com.saul.blockchainlearning.dto;

import lombok.Data;

@Data
public class TransactionDTO {
    private String public_key;
    private String sender;
    private String recipient;
    private double amount;
}
