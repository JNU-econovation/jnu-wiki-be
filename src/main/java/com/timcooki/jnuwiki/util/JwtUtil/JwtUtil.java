package com.timcooki.jnuwiki.util.JwtUtil;

import com.timcooki.jnuwiki.domain.member.DTO.response.JwtAndExpirationDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JwtUtil {

    // 액세스 토큰 만료 시간 지정
    // TODO - 만료시간 변경
    private static final Long accessTokenExpiredMS = 1000 * 60 * 300L; // 3분
    private static final Long refreshTokenExpiredMS = 1000 * 60 * 6000L * 24; // 1일

    public static String getMemberRole(String token, String secretKey){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .get("memberRole", String.class);
    }

    // 사용자 이름 토큰에서 빼내기
    public static String getMemberEmail(String token, String secretKey){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .get("memberEmail", String.class);
    }

    // 토큰 만료시간 체크
    public static boolean isExpired(String token, String secretKey){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

    public static Long getExpiration(String token, String secretKey){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .toInstant()
                .toEpochMilli();
    }

    // Toekn 생성
    public static String createJwt(String memberEmail,String memberRole, String secretKey){
        /*
        jwt는 원하는 정보를 담아둘 수 있는 Claims를 제공한다.
         */
        Claims claims = createClaim(memberEmail, memberRole);
        return createJwt(claims, accessTokenExpiredMS, secretKey);
    }

    public static JwtAndExpirationDTO createRefreshToken(String memberEmail, String memberRole, String secretKey){
        /*
        jwt는 원하는 정보를 담아둘 수 있는 Claims를 제공한다.
         */
        Claims claims = createClaim(memberEmail, memberRole);
        return createJwtAndExpiration(claims, refreshTokenExpiredMS, secretKey);
    }

    private static String createJwt(Claims claims, Long refreshTokenExpiredMS, String secretKey) {
        return "Bearer " + Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiredMS)) // 만료시간
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화
                .compact();
    }
    private static JwtAndExpirationDTO createJwtAndExpiration(Claims claims, Long refreshTokenExpiredMS, String secretKey) {
        Date expiration = new Date(System.currentTimeMillis() + refreshTokenExpiredMS);
        String refreshToken = "Bearer " + Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiration) // 만료시간
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화
                .compact();
        return JwtAndExpirationDTO.builder()
                .jwt(refreshToken)
                .Expiration(expiration.toInstant())
                .build();
    }

    private static Claims createClaim(String memberEmail, String memberRole) {
        Claims claims = Jwts.claims();
        claims.put("memberEmail", memberEmail);
        claims.put("memberRole", memberRole);
        return claims;
    }
}
