package com.timcooki.jnuwiki.util.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    // 사용자 이름 토큰에서 빼내기
    public static String getMemberName(String token, String secretKey){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .get("memberName", String.class);
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
    public static String createJwt(String memberName, String secretKey, Long expiredMs){
        /*
        jwt는 원하는 정보를 담아둘 수 있는 Claims를 제공한다.
         */
        Claims claims = Jwts.claims();
        claims.put("memberName", memberName);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs)) // 만료시간
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화
                .compact();
    }

//    // refresh Token 생성
//    public static String createRefreshToken(String secretKey, Long expiredMs){
//        Claims claims = Jwts.claims();
//        // refresh Token은 만료기간만 넣는다.
//        return Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
//                .signWith(SignatureAlgorithm.HS256, secretKey)
//                .compact();
//    }
}
