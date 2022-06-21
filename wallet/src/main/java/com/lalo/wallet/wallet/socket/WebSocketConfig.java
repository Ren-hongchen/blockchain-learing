package com.lalo.wallet.wallet.socket;

import lombok.NonNull;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;


@Configuration
public class WebSocketConfig implements EnvironmentAware {

    private Environment env;

    public String PublicKey;
    public String PrivateKey;

    @Override
    public void setEnvironment(@NonNull Environment environment) {
        this.env = environment;
        this.setKey();
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    private void setKey(){
        this.PrivateKey = this.env.getProperty("PrivateKey");
        this.PublicKey = this.env.getProperty("PublicKey");
    }

    @Bean
    public String PublicKey() {
        return this.PublicKey;
    }

    @Bean
    public String PrivateKey() {
        return this.PrivateKey;
    }
}
