package com.lalo.wallet.wallet.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserTxDTO {
    private String private_key;
    private String public_key;
    private List<InputDTO> inputs;
    private List<UserOutputDTO> outputs;
}
