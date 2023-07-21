package com.timcooki.jnuwiki.domain.docsRequest.controller;

import com.timcooki.jnuwiki.domain.docsRequest.dto.request.CreatedResponseWriteDTO;
import com.timcooki.jnuwiki.util.ApiUtils;
import com.timcooki.jnuwiki.domain.docsRequest.dto.request.CreatedRequestWriteDTO;
import com.timcooki.jnuwiki.domain.docsRequest.dto.request.ModifiedRequestWriteDTO;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequest;
import com.timcooki.jnuwiki.domain.docsRequest.service.DocsRequestService;
import com.timcooki.jnuwiki.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@RestController
@RequestMapping("/requests")
public class DocsRequestController {
    private final DocsRequestService docsRequestService;
    private final MemberService memberService;

    // 문서 수정 요청 작성
    @PostMapping("/update")
    public ResponseEntity<?> writeModifiedRequest(@AuthenticationPrincipal UserDetails userDetails, ModifiedRequestWriteDTO modifiedRequestWriteDto) {
        // 권한 확인
        Object checkAuthorization = checkAuthorization(userDetails);
        if (checkAuthorization != null) return checkAuthorization;

        // 요청 저장
        docsRequestService.createModifiedRequest(modifiedRequestWriteDto);

        // 요청 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiUtils.success(new CreatedResponseWriteDTO("요청이 저장되었습니다.")));
    }

    // 문서 생성 요청 작성
    @PostMapping("/new")
    public ResponseEntity<?> writeCreateRequest(@AuthenticationPrincipal UserDetails userDetails, CreatedRequestWriteDTO createdRequestWriteDto) {
        // TODO - CustomUserDetails | findByEmail
        // 권한 확인
        Object checkAuthorization = checkAuthorization(userDetails);
        if (checkAuthorization != null) return checkAuthorization;

        // 문서 저장
        DocsRequest createdCreatedRequest = docsRequestService.createCreatedRequest(createdRequestWriteDto);

        // 요청 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiUtils.success(new CreatedResponseWriteDTO("요청이 저장되었습니다.")));
    }

    // 권한 확인 메서드
    private Object checkAuthorization(UserDetails userDetails) {

        // 회원 여부 확인
        boolean isMember = checkMembership(userDetails.getUsername());
        if (!isMember) {
            return ApiUtils.error("로그인이 필요한 기능입니다.", HttpStatus.UNAUTHORIZED);
        }

        // 회원 가입 일수 확인
        // TODO: UserDetails 구현 완료되고 나서 getCreatedAt() 에러 해결되는지 확인
        boolean isWithin15Days = checkMembershipDuration(userDetails.getCreatedAt());
        if (!isWithin15Days) {
            return ApiUtils.error("회원가입 후 15일이 지나야 요청이 가능합니다.", HttpStatus.FORBIDDEN);
        }

        return null;
    }

    // 유효한 유저인지 확인하는 메서드
    private boolean checkMembership(String username) {
        return memberService.hasMember(username);
    }

    // 유저의 회원가입 일자가 15일이 맞는지 확인하는 메서드
    private boolean checkMembershipDuration(LocalDate createdAt) {
        LocalDate currentDate = LocalDate.now();

        long daysBetween = ChronoUnit.DAYS.between(createdAt, currentDate);
        return daysBetween > 15; // 15일 이내인 경우 false를 반환
    }
}
