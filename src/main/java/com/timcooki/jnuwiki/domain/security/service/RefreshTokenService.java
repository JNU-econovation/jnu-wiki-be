package com.timcooki.jnuwiki.domain.security.service;

import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.domain.security.entity.RefreshToken;
import com.timcooki.jnuwiki.domain.security.repository.RefreshTokenRepository;
import com.timcooki.jnuwiki.util.ApiUtils;
import com.timcooki.jnuwiki.util.JwtUtil.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

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

    public ResponseEntity<?> renewToken(String refreshToken){
        Member member = findByToken(refreshToken).map(this::verifyExpiration)
                .map(RefreshToken::getMember)
                .orElseThrow(() -> new RuntimeException("인증되지 않은 토큰입니다."));

        String accessToken = JwtUtil.createJwt(member.getEmail(), member.getRole().toString(), secretKey);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .body(ApiUtils.success(null));
    }

    public RefreshToken createRefreshToken(String email, Optional<Member> member) {
        RefreshToken refreshToken = RefreshToken.builder()
                .member(member.get())
                .token(UUID.randomUUID().toString())
                .expiredDate(Instant.now().plusMillis(1000*60*60))//1시간
                .build();
        return refreshTokenRepository.save(refreshToken);
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
