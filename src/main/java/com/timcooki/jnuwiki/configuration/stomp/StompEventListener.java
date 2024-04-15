package com.timcooki.jnuwiki.configuration.stomp;

import com.timcooki.jnuwiki.domain.docsRequest.repository.DocsStatusRepository;
import com.timcooki.jnuwiki.domain.security.config.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
@RequiredArgsConstructor
public class StompEventListener {

    private static final String AUTHORIZATION = "Authorization";

    private final DocsStatusRepository docsStatusRepository;


    @EventListener
    public void handleStompDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String authorizationHeader = String.valueOf(headerAccessor.getNativeHeader(AUTHORIZATION));

        if(authorizationHeader == null || authorizationHeader.equals("null")){
            throw new MessageDeliveryException("err1 : 인증이 되지 않은 사용자입니다.");
        }

        String token = authorizationHeader.substring(JwtProvider.PREFIX.length());

        Claims claims;
        try{
            claims = JwtProvider.getClaims(token);
            String email = String.valueOf(claims.get("memberEmail"));
            docsStatusRepository.deleteByEmail(email);
        }catch (MessageDeliveryException e){
            log.error("STOMP ERROR : " + e.getMessage());
            throw new MessageDeliveryException("err2 : 인증이 되지 않은 사용자입니다.");
        }catch (MalformedJwtException e){
            log.error("STOMP ERROR : " + e.getMessage());
            throw new MessageDeliveryException("err3 : 인증이 되지 않은 사용자입니다.");
        }catch (Exception e){
            log.error("ERROR : " + e.getMessage());
        }



    }

}
