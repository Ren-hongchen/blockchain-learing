package com.saul.blockchainlearning.dto;

import lombok.Data;

@Data
public class OutputDTO {
    private double value;
    private int script_length;
    private String script;
}
