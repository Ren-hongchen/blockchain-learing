package com.lalo.wallet.wallet.socket;

import com.lalo.wallet.wallet.algorithm.ECDSA;
import com.lalo.wallet.wallet.algorithm.Hash256;
import com.lalo.wallet.wallet.dto.CertificationDTO;
import com.lalo.wallet.wallet.entity.ServerInfo;
import com.lalo.wallet.wallet.serialization.Serializer;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@Slf4j
@ServerEndpoint("/websocket/{userId}")
public class WebSocket{

    @Autowired
    String PrivateKey;

    @Autowired
    String PublicKey;

    private final KeyPair keyPair = ECDSA.getKeyPair(); //server key pair

    private Session session;

    private static final CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();

    private static final Map<String,Session> sessionPool = new HashMap<String,Session>();

    @OnOpen
    public void onOpen(Session session, @PathParam(value="userId")String userId) {
        try {
            this.session = session;
            webSockets.add(this);
            sessionPool.put(userId, session);
            log.info("【websocket消息】有新的连接，总数为:"+webSockets.size());

            ServerInfo serverInfo = new ServerInfo();
            serverInfo.setName("bitcoin-wallet");
            assert keyPair != null;
            serverInfo.setPublic_key(ECDSA.getPublicKeyStrFromPublicKey(keyPair));
            log.info(serverInfo.toString());
            CertificationDTO certificationDTO = new CertificationDTO();
            certificationDTO.setServerInfo(serverInfo);
            byte[] hash_byte = Hash256.SHA256(Serializer.serialize(serverInfo));
            String hash = Hex.toHexString(hash_byte);
            log.info(hash);
            log.info(PrivateKey);
            log.info(PublicKey);
            String signature = ECDSA.sign(hash, PrivateKey);
            log.info(signature);
            certificationDTO.setSignature(signature);
            log.info(certificationDTO.toString());
            session.getAsyncRemote().sendObject(certificationDTO);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    /**
     * 链接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        try {
            webSockets.remove(this);
            log.info("【websocket消息】连接断开，总数为:"+webSockets.size());
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("【websocket消息】收到客户端消息:"+message);
    }

    /** 发送错误时的处理
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error(error.getMessage());
        error.printStackTrace();
    }


    // 此为广播消息
    public void sendAllMessage(String message) {
        log.info("【websocket消息】广播消息:"+message);
        for(WebSocket webSocket : webSockets) {
            try {
                if(webSocket.session.isOpen()) {
                    webSocket.session.getAsyncRemote().sendText(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 此为单点消息
    public void sendOneMessage(String userId, String message) {
        Session session = sessionPool.get(userId);
        if (session != null&&session.isOpen()) {
            try {
                log.info("【websocket消息】 单点消息:"+message);
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 此为单点消息(多人)
    public void sendMoreMessage(String[] userIds, String message) {
        for(String userId:userIds) {
            Session session = sessionPool.get(userId);
            if (session != null&&session.isOpen()) {
                try {
                    log.info("【websocket消息】 单点消息:"+message);
                    session.getAsyncRemote().sendText(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
