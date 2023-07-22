package com.timcooki.jnuwiki.domain.member.controller;

import com.timcooki.jnuwiki.domain.docs.DTO.response.InfoEditResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.NewListReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.NewReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.EditListReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.EditReadResDTO;
import com.timcooki.jnuwiki.util.ApiUtils;
import com.timcooki.jnuwiki.domain.docsRequest.service.DocsRequestService;
import com.timcooki.jnuwiki.domain.member.service.AdminService;
import com.timcooki.jnuwiki.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final MemberService memberService;
    private final DocsRequestService docsRequestService;
    private final AdminService adminService;

    // 문서 기본정보 수정 요청 목록 조회
    @GetMapping("/requests/update/{page}")
    private ResponseEntity<?> getModifiedRequests(@AuthenticationPrincipal UserDetails userDetails, @PathVariable int page,
                                       @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        // 권한 확인

        // 요청 목록 조회
        EditListReadResDTO modifiedRequests = docsRequestService.getModifiedRequestList(pageable);
        return ResponseEntity.ok().body(ApiUtils.success(modifiedRequests));
    }

    // 새 문서 생성 요청 목록 조회
    @GetMapping("/requests/new/{page}")
    private Object getCreatedRequests(@AuthenticationPrincipal UserDetails userDetails, @PathVariable int page,
                                      @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        // 권한 확인

        // 요청 목록 조회
        NewListReadResDTO createRequests = docsRequestService.getCreatedRequestList(pageable);
        return ApiUtils.success(createRequests);
    }

    // 문서 기본 정보 수정 요청 상세 조회
    @GetMapping("/requests/update/{docs_request_id}")
    private Object getOneModifiedRequest(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("docs_request_id") Long docsRequestId) {
        // 권한 확인


        // 요청 존재 확인


        EditReadResDTO modifiedRequest = docsRequestService.getOneModifiedRequest(docsRequestId);
        return ApiUtils.success(modifiedRequest);
    }

    // 새 문서 신청 요청 상세 조회
    @GetMapping("/requests/new/{docs_request_id}")
    private Object getOneCreatedRequest(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("docs_request_id") Long docsRequestId) {
        // 권한 확인


        // 요청 존재 확인

        NewReadResDTO modifiedRequest = docsRequestService.getOneCreatedRequest(docsRequestId);
        return ApiUtils.success(modifiedRequest);
    }

    // TODO - 예외처리 수정
    // 새 문서 생성 요청 승락 생성
    @PostMapping("/approve/new/{docs_request_id}")
    private ResponseEntity<?> approveCreateRequest(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("docs_request_id") Long docsRequestId) {


        try{
            return ResponseEntity.ok(ApiUtils.success(adminService.approveNewDocs(userDetails, docsRequestId)));
        }catch (Exception e){
            return ResponseEntity.status(500).body(ApiUtils.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        }

    }

    // 문서 수정 요청 승락
    @PostMapping("/approve/update/{docs_request_id}")
    private ResponseEntity<?> approveModifiedRequest(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("docs_request_id") Long docsRequestId) {
        // 권한 확인


        // 요청 존재 확인


        InfoEditResDTO updatedDocs = adminService.updateDocsFromRequest(docsRequestId);
        return ResponseEntity.ok().body(ApiUtils.success(updatedDocs));
    }

    // 문서 요청 반려
    @PostMapping("/reject/{docs_request_id}")
    private ResponseEntity<?> rejectRequest(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("docs_request_id") Long docsRequestId) {

        try {
            docsRequestService.rejectRequest(docsRequestId);
            return ResponseEntity.ok(ApiUtils.success("요청이 반려되었습니다."));
        }catch (Exception e){
            return ResponseEntity.status(400).body(ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }
}
