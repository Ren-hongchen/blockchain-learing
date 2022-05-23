package com.lalo.wallet.wallet.dto;

import lombok.Data;

@Data
public class UserOutputDTO {
    private String address; //public key
    private double amount;
}
