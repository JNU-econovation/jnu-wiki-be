package com.timcooki.jnuwiki.domain.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Instant;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {
    private static String secretKey;
    public static final String PREFIX = "Bearer ";
    private static final Long HOUR = 1000L * 60 * 60;
    private static final Long ACCESS_TOKEN_EXP_MS = HOUR; // 1시간
    private static final Long REFRESH_TOKEN_EXP_MS = 24 * HOUR * 14; // 2주

    @Value("${jwt.secret}")
    public void setSecretKey(String secretKey) {
        JwtProvider.secretKey = secretKey;
    }

    private static Claims createClaim(String memberEmail, String memberRole) {
        Claims claims = Jwts.claims();
        claims.put("memberEmail", memberEmail);
        claims.put("memberRole", memberRole);
        return claims;
    }

    public static String createAccessToken(String memberEmail, String memberRole) {
        return PREFIX + Jwts.builder()
                .setClaims(createClaim(memberEmail, memberRole))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXP_MS))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public static String createRefreshToken(String memberEmail, String memberRole) {
        return PREFIX + Jwts.builder()
                .setClaims(createClaim(memberEmail, memberRole))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXP_MS))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public static String cutTokenPrefix(String bearerToken) {
        return bearerToken.substring(7);
    }

    public static Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(cutTokenPrefix(token))
                .getBody();
    }

    public static Instant getExpiration(String token) {
        return getClaims(token)
                .getExpiration()
                .toInstant();
    }
}
