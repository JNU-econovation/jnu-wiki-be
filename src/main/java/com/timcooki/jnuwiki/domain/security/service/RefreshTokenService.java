package com.timcooki.jnuwiki.domain.security.service;

import com.timcooki.jnuwiki.domain.member.DTO.response.JwtAndExpirationDTO;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.domain.security.entity.RefreshToken;
import com.timcooki.jnuwiki.domain.security.repository.RefreshTokenRepository;
import com.timcooki.jnuwiki.util.JwtUtil.JwtUtil;
import com.timcooki.jnuwiki.util.errors.exception.Exception401;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.secret}")
    private String secretKey;

    @Autowired
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String renewToken(String refreshToken){
        Member member = findByToken(refreshToken).map(this::verifyExpiration)
                .map(RefreshToken::getMember)
                .orElseThrow(() -> new Exception401("인증되지 않은 토큰입니다."));

        return JwtUtil.createJwt(member.getEmail(), member.getRole().toString(), secretKey);
    }


    public RefreshToken createRefreshToken(Member member, String secretKey) {
        JwtAndExpirationDTO jwtAndExpiration = JwtUtil.createRefreshToken(member.getEmail(), member.getRole().toString(), secretKey);
        // 로그인을 이미 한 유저라면?
        return refreshTokenRepository.findByMemberAndExpiredDateIsAfter(member, Instant.now()).orElse(
                RefreshToken.builder()
                        .member(member)
                        .token(jwtAndExpiration.jwt())
                        .expiredDate(jwtAndExpiration.Expiration())
                        .build()
        );
    }


    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }


    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiredDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token was expired. Please make a new signin request");
        }
        return token;
    }
}
