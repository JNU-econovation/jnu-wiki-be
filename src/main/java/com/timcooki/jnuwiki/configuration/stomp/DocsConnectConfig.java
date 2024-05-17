package com.timcooki.jnuwiki.configuration.stomp;

import com.timcooki.jnuwiki.domain.docsRequest.error.DocsErrorHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class DocsConnectConfig implements WebSocketMessageBrokerConfigurer {

    private final DocsPreHandler docsPreHandler;
    private final DocsErrorHandler docsErrorHandler;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/queue");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/api/connect")
                .setAllowedOriginPatterns("*")
                .withSockJS();
//        registry.setErrorHandler(docsErrorHandler);
    }

//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration){
//        registration.interceptors(docsPreHandler);
//    }



}
