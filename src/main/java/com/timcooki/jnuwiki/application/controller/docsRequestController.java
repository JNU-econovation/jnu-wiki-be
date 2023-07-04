package com.timcooki.jnuwiki.application.controller;

import com.timcooki.jnuwiki.domain.docsRequest.dto.CreatedRequestDto;
import com.timcooki.jnuwiki.domain.docsRequest.dto.ModifiedRequestDto;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequest;
import com.timcooki.jnuwiki.domain.docsRequest.service.DocsRequestService;
import com.timcooki.jnuwiki.domain.member.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@RestController
@RequestMapping("/requests")
public class DocsRequestController {
    private final DocsRequestService docsRequestService;
    private final MemberService memberService;

    @Value("${jwt.secret}") // JWT 비밀 키를 application.properties에 설정하고 주입받음
    private String jwtSecret;
    // TODO : parser 말고 @AuthenticationPrinciple 사용

    // 문서 수정 요청 작성
    @PostMapping("/update")
    public ResponseEntity writeModifiedRequest(@RequestBody ModifiedRequestDto modifiedRequestDto, @RequestHeader("Authorization") String authorizationHeader) {
        // 권한 확인
        ResponseEntity checkAuthorization = checkAuthorization(authorizationHeader);
        if (checkAuthorization(authorizationHeader) != null) return checkAuthorization;

        // 문서 저장
        DocsRequest createdRequest = docsRequestService.createModifiedRequest(modifiedRequestDto);

        // 요청 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    }

    // 문서 생성 요청 작성
    @PostMapping("/new")
    public ResponseEntity writeCreateRequest(@RequestBody CreatedRequestDto createdRequestDto, @RequestHeader("Authorization") String authorizationHeader) {
        // 권한 확인
        ResponseEntity checkAuthorization = checkAuthorization(authorizationHeader);
        if (checkAuthorization(authorizationHeader) != null) return checkAuthorization;

        // 문서 저장
        DocsRequest createdCreatedRequest = docsRequestService.createCreatedRequest(createdRequestDto);

        // 요청 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCreatedRequest);
    }

    // 권한 확인 메서드
    private ResponseEntity<String> checkAuthorization(String authorizationHeader) {
        // Authorization 헤더에서 JWT 토큰을 추출
        String jwtToken = authorizationHeader.replace("Bearer ", "");

        // JWT 토큰을 파싱하여 클레임(claim)을 추출합니다.
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwtToken).getBody();

        // 회원 여부 확인
        boolean isMember = checkMembership(claims);
        if (!isMember) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요한 기능입니다.");
        }

        // 회원 가입 일수 확인
        boolean isWithin15Days = checkMembershipDuration(claims);
        if (!isWithin15Days) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("회원가입 후 15일이 지나야 요청이 가능합니다.");
        }

        return null;
    }

    // 유효한 유저인지 확인하는 메서드
    private boolean checkMembership(Claims claims) {
        String memberId = claims.get("memberId", String.class);
        return memberService.hasMember(memberId);
    }

    // 유저의 회원가입 일자가 15일이 맞는지 확인하는 메서드
    private boolean checkMembershipDuration(Claims claims) {
        // TODO: 회원의 회원가입 날짜 정보를 가지고 있는 claim 이름이 createdAt이 맞는지 확인하기
        LocalDate registrationDate = claims.get("createdAt", LocalDate.class);
        LocalDate currentDate = LocalDate.now();

        long daysBetween = ChronoUnit.DAYS.between(registrationDate, currentDate);
        return daysBetween > 15; // 15일 이내인 경우 false를 반환
    }
}
