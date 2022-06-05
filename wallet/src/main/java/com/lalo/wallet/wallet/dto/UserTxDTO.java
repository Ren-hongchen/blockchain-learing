package com.lalo.wallet.wallet.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserTxDTO {
    private List<UserOutputDTO> outputs;
}
