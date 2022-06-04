package com.lalo.wallet.wallet.dto;

import lombok.Data;

@Data
public class OutputDTO {
    private double value;
    private ScriptPubKey script;
}
