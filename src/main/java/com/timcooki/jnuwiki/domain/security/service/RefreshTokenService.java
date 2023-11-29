package com.timcooki.jnuwiki.domain.security.service;

import static com.timcooki.jnuwiki.domain.security.config.JwtProvider.PREFIX;

import com.timcooki.jnuwiki.domain.member.DTO.response.AccessTokenResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.WrapAccessTokenResDTO;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.domain.security.entity.RefreshToken;
import com.timcooki.jnuwiki.domain.security.repository.RefreshTokenRepository;
import com.timcooki.jnuwiki.domain.security.config.JwtProvider;
import com.timcooki.jnuwiki.util.TimeFormatter;
import com.timcooki.jnuwiki.util.errors.exception.Exception401;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public WrapAccessTokenResDTO renewAccessToken(String refreshToken) {
        refreshToken = PREFIX + refreshToken;
        RefreshToken existToken = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new Exception401("인증되지 않은 토큰입니다."));

        verifyExpiration(existToken);
        Member member = existToken.getMember();
        String accessToken = JwtProvider.createAccessToken(member.getEmail(), member.getRole().toString());
        Instant expiration = JwtProvider.getExpiration(accessToken);

        return WrapAccessTokenResDTO.builder()
                .accessToken(accessToken)
                .accessTokenResDTO(AccessTokenResDTO.builder()
                        .accessTokenExpiration(expiration.toEpochMilli())
                        .accessTokenFormattedExpiration(TimeFormatter.format(expiration))
                        .build())
                .build();
    }


    public RefreshToken createRefreshToken(Member member) {
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
            throw new Exception401("리프레시 토큰이 만료되었습니다. 재로그인을 해주세요.");
        }
    }
}
