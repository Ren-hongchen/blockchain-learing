package com.saul.blockchainlearning.model;


import lombok.Data;

@Data
public class Transaction {
    private String sender;
    private String recipient;
    private double amount;
}
