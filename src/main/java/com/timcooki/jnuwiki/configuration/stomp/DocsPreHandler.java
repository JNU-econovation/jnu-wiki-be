package com.timcooki.jnuwiki.configuration.stomp;

import com.timcooki.jnuwiki.domain.security.config.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class DocsPreHandler implements ChannelInterceptor {

    private static final String AUTHORIZATION = "Authorization";
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);

        String authorizationHeader = String.valueOf(headerAccessor.getNativeHeader(AUTHORIZATION));

        if(authorizationHeader == null || authorizationHeader.equals("null")){
            throw new MessageDeliveryException("err1 : 인증이 되지 않은 사용자입니다.");
        }

        String token = authorizationHeader.substring(JwtProvider.PREFIX.length());

        Claims claims;
        try{
            claims = JwtProvider.getClaims(token);
            //TODO : spring security 혹은 최소한의 보안만
        }catch (MessageDeliveryException e){
            log.error("STOMP ERROR : " + e.getMessage());
            throw new MessageDeliveryException("err2 : 인증이 되지 않은 사용자입니다.");
        }catch (MalformedJwtException e){
            log.error("STOMP ERROR : " + e.getMessage());
            throw new MessageDeliveryException("err3 : 인증이 되지 않은 사용자입니다.");
        }


        return message;
    }
}
