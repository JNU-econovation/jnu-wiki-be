package com.timcooki.jnuwiki.domain.security.config;


import com.timcooki.jnuwiki.domain.security.service.MemberSecurityService;
import com.timcooki.jnuwiki.util.JwtUtil.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final MemberSecurityService memberSecurityService;
    //@Value("${jwt.secret}")
    private final String secretKey="test.test";


    @Override
    // 인증 필터
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // HEADER/AUTHORIZATION에서 ACCESSTOKEN을 가져온다.
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        // toekn이 없는경우 || 토근이 Bearer로 시작하지 않는 경우
        if(authorization==null || !authorization.startsWith("Bearer ")){
            //log.error("authorization 을 잘못 보냈습니다..");
            filterChain.doFilter(request, response);
            return;
        }

        // Token 꺼내기. Bearer를 제거하고 TOKEN값을 가져온다.
        String token = authorization.split(" ")[1];

        // Token Expired되었는지 여부 판단.
        if (JwtUtil.isExpired(token, secretKey)){
            log.error("Token이 만료되었습니다.");
            filterChain.doFilter(request,response);
            return;
        }

        // MemberEmail을 Token에서 꺼낸다.
        String memberEmail = JwtUtil.getMemberEmail(token, secretKey);
        log.info("memberEmail: {}", memberEmail);
        // MemberRole을 Token에서 꺼낸다. // TODO - 삭제(log용)
        String memberRole = JwtUtil.getMemberRole(token, secretKey);
        log.info("memberRole: {}", memberRole);


        UserDetails userDetails = memberSecurityService.loadUserByUsername(memberEmail);
        // 권한 부여
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        // Detail 넣어줌
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken); // request에 인증 도장이 찍힘
        filterChain.doFilter(request, response);

    }
}
