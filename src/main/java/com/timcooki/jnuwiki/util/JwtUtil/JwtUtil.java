package com.timcooki.jnuwiki.util.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    // 액세스 토큰 만료 시간 지정
    // TODO - 만료시간 변경
    private static final Long accessTokenExpiredMS = 1000 * 60 * 3L; // 3분

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

    // Toekn 생성
    public static String createJwt(String memberEmail,String memberRole, String secretKey){
        /*
        jwt는 원하는 정보를 담아둘 수 있는 Claims를 제공한다.
         */
        Claims claims = Jwts.claims();
        claims.put("memberEmail", memberEmail);
        claims.put("memberRole", memberRole);

        return "Bearer " + Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiredMS)) // 만료시간
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화
                .compact();
    }
}
