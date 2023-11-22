package com.timcooki.jnuwiki.domain.security.service;

import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.domain.security.entity.RefreshToken;
import com.timcooki.jnuwiki.domain.security.repository.RefreshTokenRepository;
import com.timcooki.jnuwiki.domain.security.config.JwtProvider;
import com.timcooki.jnuwiki.util.errors.exception.Exception401;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    // refreshToken이 만료되지 않았다면 accessToken을 재발급
    public String renewAccessToken(String refreshToken) {
        RefreshToken existToken = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new Exception401("인증되지 않은 토큰입니다."));

        verifyExpiration(existToken);

        Member member = existToken.getMember();
        return JwtProvider.createAccessToken(member.getEmail(), member.getRole().toString());
    }


    public RefreshToken createRefreshToken(Member member) {
        if (refreshTokenRepository.existsByMemberAndExpiredDateIsAfter(member, Instant.now())) {
            return refreshTokenRepository.findByMemberAndExpiredDateIsAfter(member, Instant.now()).get();
        }

        String refreshToken = JwtProvider.createRefreshToken(member.getEmail(), member.getRole().toString());
        Instant expiration = JwtProvider.getExpiration(refreshToken);

        return refreshTokenRepository.save(
                RefreshToken.builder()
                        .member(member)
                        .token(refreshToken)
                        .expiredDate(expiration)
                        .build());
    }

    public void verifyExpiration(RefreshToken token) {
        if (token.getExpiredDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token was expired. Please make a new signin request");
        }
    }
}
