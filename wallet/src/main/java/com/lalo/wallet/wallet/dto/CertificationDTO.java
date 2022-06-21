package com.lalo.wallet.wallet.dto;

import com.lalo.wallet.wallet.entity.ServerInfo;
import lombok.Data;

@Data
public class CertificationDTO {
    private ServerInfo serverInfo;
    private String signature;
}
