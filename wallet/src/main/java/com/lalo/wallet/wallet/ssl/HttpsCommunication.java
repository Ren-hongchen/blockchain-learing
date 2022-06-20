package com.lalo.wallet.wallet.ssl;

import com.lalo.wallet.wallet.algorithm.ECDSA;
import com.lalo.wallet.wallet.socket.WebSocket;

import javax.annotation.Resource;
import java.security.KeyPair;

public class HttpsCommunication {
    @Resource
    private WebSocket webSocket;

    private final KeyPair keyPair = ECDSA.getKeyPair();


}
