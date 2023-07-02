package com.timcooki.jnuwiki.application.controller;

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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final MemberService memberService;
    private final DocsRequestService docsRequestService;
    @Value("${jwt.secret}") // JWT 비밀 키를 application.properties 에 설정하고 주입받음
    private String jwtSecret;

    // 문서 기본정보 수정 요청 목록 조회
    @GetMapping("/requests/update")
    private ResponseEntity<List<DocsRequest>> getModifiedRequests(@RequestHeader("Authorization") String authorizationHeader) {
        // 권한 확인
        ResponseEntity checkAuthorization = checkAuthorization(authorizationHeader);
        if (checkAuthorization != null) return checkAuthorization;

        // 요청 목록 조회
        List<DocsRequest> modifiedRequests = docsRequestService.getModifiedRequestList();
        return ResponseEntity.status(HttpStatus.OK).body(modifiedRequests);
    }

    // 새 문서 생성 요청 목록 조회
    @GetMapping("/requests/new")
    private ResponseEntity<List<DocsRequest>> getCreatedRequests(@RequestHeader("Authorization") String authorizationHeader) {
        // 권한 확인
        ResponseEntity checkAuthorization = checkAuthorization(authorizationHeader);
        if (checkAuthorization != null) return checkAuthorization;

        // 요청 목록 조회
        List<DocsRequest> createRequests = docsRequestService.getCreatedRequestList();
        return ResponseEntity.status(HttpStatus.OK).body(createRequests);
    }

    // 문서 기본 정보 수정 요청 상세 조회
    @GetMapping("/requests/update/{docs_request_id}")
    private ResponseEntity<DocsRequest> getOneModifiedRequest(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("docs_request_id") String docsRequestId) {
        // 권한 확인
        ResponseEntity checkAuthorization = checkAuthorization(authorizationHeader);
        if (checkAuthorization != null) return checkAuthorization;

        // 요청 존재 확인
        ResponseEntity checkRequest = checkValidRequest(docsRequestId);
        if (checkRequest != null) return checkRequest;

        DocsRequest modifiedRequest = docsRequestService.getOneModifiedRequest(docsRequestId);
        return ResponseEntity.status(HttpStatus.OK).body(modifiedRequest);
    }

    // 새 문서 신청 요청 상세 조회
    @GetMapping("/requests/new/{docs_request_id}")
    private ResponseEntity<DocsRequest> getOneCreatedRequest(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("docs_request_id") String docsRequestId) {
        // 권한 확인
        ResponseEntity checkAuthorization = checkAuthorization(authorizationHeader);
        if (checkAuthorization != null) return checkAuthorization;

        // 요청 존재 확인
        ResponseEntity checkRequest = checkValidRequest(docsRequestId);
        if (checkRequest != null) return checkRequest;

        DocsRequest modifiedRequest = docsRequestService.getOneCreatedRequest(docsRequestId);
        return ResponseEntity.status(HttpStatus.OK).body(modifiedRequest);
    }

    // 새 문서 생성 요청 승락 생성
    @PostMapping("/approve/new/{docs_request_id}")
    private ResponseEntity approveCreateRequest(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("docs_request_id") String docsRequestId){
        // 권한 확인
        ResponseEntity checkAuthorization = checkAuthorization(authorizationHeader);
        if (checkAuthorization != null) return checkAuthorization;

        // 요청 존재 확인
        ResponseEntity checkRequest = checkValidRequest(docsRequestId);
        if (checkRequest != null) return checkRequest;

        DocsRequest createdDocs = docsRequestService.createDocsFromRequest(docsRequestId);
        return ResponseEntity.status(HttpStatus.OK).body(createdDocs);
    }

    // 문서 수정 요청 승락
    @PostMapping("/approve/update/{docs_request_id}")
    private ResponseEntity approveModifiedRequest(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("docs_request_id") String docsRequestId){
        // 권한 확인
        ResponseEntity checkAuthorization = checkAuthorization(authorizationHeader);
        if (checkAuthorization != null) return checkAuthorization;

        // 요청 존재 확인
        ResponseEntity checkRequest = checkValidRequest(docsRequestId);
        if (checkRequest != null) return checkRequest;

        DocsRequest updatedDocs = docsRequestService.updateDocsFromRequest(docsRequestId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedDocs);
    }

    // 문서 요청 반려
    @PostMapping("/reject/{docs_request_id}")
    private ResponseEntity rejectRequest(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("docs_request_id") String docsRequestId){
        // 권한 확인
        ResponseEntity checkAuthorization = checkAuthorization(authorizationHeader);
        if (checkAuthorization != null) return checkAuthorization;

        // 요청 존재 확인
        ResponseEntity checkRequest = checkValidRequest(docsRequestId);
        if (checkRequest != null) return checkRequest;

        docsRequestService.rejectRequest(docsRequestId);
        return ResponseEntity.status(HttpStatus.OK).body("요청이 반려되었습니다.");
    }

    // 권한 확인 메서드
    private ResponseEntity<String> checkAuthorization(String authorizationHeader) {
        // Authorization 헤더에서 JWT 토큰을 추출
        String jwtToken = authorizationHeader.replace("Bearer ", "");

        // JWT 토큰을 파싱하여 클레임(claim)을 추출
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwtToken).getBody();

        // 회원 여부 확인
        boolean isMember = checkMembership(claims);
        if (!isMember) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요한 기능입니다.");
        }

        // 어드민 권한 확인
        boolean isAdmin = checkAdminAuthor(claims);
        if (!isAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("관리자 권한이 없습니다.");
        }

        return null;
    }

    // 요청 존재 확인 메서드
    private ResponseEntity<String> checkValidRequest(String docsRequestId) {
        // 요청 확인
        boolean isExist = checkExistRequest(docsRequestId);
        if (!isExist) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 요청입니다.");
        }

        return null;
    }

    // 존재하는 요청인지 확인하는 메서드
    private boolean checkExistRequest(String docsRequestId) {
        return docsRequestService.hasRequest(docsRequestId);
    }

    // 관리자인지 확인하는 메서드
    private boolean checkAdminAuthor(Claims claims) {
        String role = claims.get("role", String.class);
        return role == "admin";
    }

    // 유효한 유저인지 확인하는 메서드
    private boolean checkMembership(Claims claims) {
        String memberId = claims.get("memberId", String.class);
        return memberService.hasMember(memberId);
    }
}
