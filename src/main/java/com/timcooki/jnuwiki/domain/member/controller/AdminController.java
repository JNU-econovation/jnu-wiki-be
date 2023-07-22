package com.timcooki.jnuwiki.domain.member.controller;

import com.timcooki.jnuwiki.domain.docs.DTO.response.InfoEditResDTO;
import com.timcooki.jnuwiki.domain.docsRequest.service.DocsRequestService;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.EditListReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.EditReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.NewListReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.NewReadResDTO;
import com.timcooki.jnuwiki.domain.member.service.MemberService;
import com.timcooki.jnuwiki.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final MemberService memberService;
    private final DocsRequestService docsRequestService;

    // 문서 기본정보 수정 요청 목록 조회
    @GetMapping("/requests/update/{page}")
    private ResponseEntity<?> getModifiedRequests(@AuthenticationPrincipal UserDetails userDetails, @PathVariable int page,
                                       @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        // 권한 확인
        Object checkAuthorization = checkAuthorization(userDetails);
        if (checkAuthorization != null) return checkAuthorization;

        // 요청 목록 조회
        EditListReadResDTO modifiedRequests = docsRequestService.getModifiedRequestList(pageable);
        return ResponseEntity.ok().body(ApiUtils.success(modifiedRequests));
    }

    // 새 문서 생성 요청 목록 조회
    @GetMapping("/requests/new/{page}")
    private Object getCreatedRequests(@AuthenticationPrincipal UserDetails userDetails, @PathVariable int page,
                                      @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        // 권한 확인
        Object checkAuthorization = checkAuthorization(userDetails);
        if (checkAuthorization != null) return checkAuthorization;

        // 요청 목록 조회
        Page<NewListReadResDTO> createRequests = docsRequestService.getCreatedRequestList(pageable);
        return ApiUtils.success(createRequests);
    }

    // 문서 기본 정보 수정 요청 상세 조회
    @GetMapping("/requests/update/{docs_request_id}")
    private Object getOneModifiedRequest(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("docs_request_id") Long docsRequestId) {
        // 권한 확인
        Object checkAuthorization = checkAuthorization(userDetails);
        if (checkAuthorization != null) return checkAuthorization;

        // 요청 존재 확인
        Object checkRequest = checkValidRequest(docsRequestId);
        if (checkRequest != null) return checkRequest;

        EditReadResDTO modifiedRequest = docsRequestService.getOneModifiedRequest(docsRequestId);
        return ApiUtils.success(modifiedRequest);
    }

    // 새 문서 신청 요청 상세 조회
    @GetMapping("/requests/new/{docs_request_id}")
    private Object getOneCreatedRequest(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("docs_request_id") String docsRequestId) {
        // 권한 확인
        Object checkAuthorization = checkAuthorization(userDetails);
        if (checkAuthorization != null) return checkAuthorization;

        // 요청 존재 확인
        Object checkRequest = checkValidRequest(docsRequestId);
        if (checkRequest != null) return checkRequest;

        NewReadResDTO modifiedRequest = docsRequestService.getOneCreatedRequest(docsRequestId);
        return ApiUtils.success(modifiedRequest);
    }

    // 새 문서 생성 요청 승락 생성
    @PostMapping("/approve/new/{docs_request_id}")
    private Object approveCreateRequest(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("docs_request_id") String docsRequestId) {
        // 권한 확인
        Object checkAuthorization = checkAuthorization(userDetails);
        if (checkAuthorization != null) return checkAuthorization;

        // 요청 존재 확인
        Object checkRequest = checkValidRequest(docsRequestId);
        if (checkRequest != null) return checkRequest;

        DocsCreateResDTO createdDocs = docsRequestService.createDocsFromRequest(docsRequestId);
        return ApiUtils.success(createdDocs);
    }

    // 문서 수정 요청 승락
    @PostMapping("/approve/update/{docs_request_id}")
    private ResponseEntity<?> approveModifiedRequest(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("docs_request_id") Long docsRequestId) {
        // 권한 확인
        Object checkAuthorization = checkAuthorization(userDetails);
        if (checkAuthorization != null) return checkAuthorization;

        // 요청 존재 확인
        Object checkRequest = checkValidRequest(docsRequestId);
        if (checkRequest != null) return checkRequest;

        InfoEditResDTO updatedDocs = docsRequestService.updateDocsFromRequest(docsRequestId);
        return ResponseEntity.ok().body(ApiUtils.success(updatedDocs));
    }

    // 문서 요청 반려
    @PostMapping("/reject/{docs_request_id}")
    private Object rejectRequest(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("docs_request_id") String docsRequestId) {
        // 권한 확인
        Object checkAuthorization = checkAuthorization(userDetails);
        if (checkAuthorization != null) return checkAuthorization;

        // 요청 존재 확인
        Object checkRequest = checkValidRequest(docsRequestId);
        if (checkRequest != null) return checkRequest;

        docsRequestService.rejectRequest(docsRequestId);
        return ApiUtils.success("요청이 반려되었습니다.");
    }

    // 권한 확인 메서드
    private Object checkAuthorization(UserDetails userDetails) {
        // 회원 여부 확인
        boolean isMember = checkMembership(userDetails.getUsername());
        if (!isMember) {
            return ApiUtils.error("로그인이 필요한 기능입니다.", HttpStatus.UNAUTHORIZED);
        }

        // 어드민 권한 확인
        boolean isAdmin = checkAdminAuthor(userDetails.getAuthorities());
        if (!isAdmin) {
            return ApiUtils.error("관리자 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        return null;
    }

    // 요청 존재 확인 메서드
    private Object checkValidRequest(Long docsRequestId) {
        // 요청 확인
        boolean isExist = checkExistRequest(docsRequestId);
        if (!isExist) {
            return ApiUtils.error("존재하지 않는 요청입니다.", HttpStatus.NOT_FOUND);
        }

        return null;
    }

    // 존재하는 요청인지 확인하는 메서드
    private boolean checkExistRequest(Long docsRequestId) {
        return docsRequestService.hasRequest(docsRequestId);
    }

    // 관리자인지 확인하는 메서드
    private boolean checkAdminAuthor(Collection<? extends GrantedAuthority> authorities) {
        return authorities.contains("admin");
    }

    // 유효한 유저인지 확인하는 메서드
    private boolean checkMembership(String username) {
        return memberService.hasMember(username);
    }
}
