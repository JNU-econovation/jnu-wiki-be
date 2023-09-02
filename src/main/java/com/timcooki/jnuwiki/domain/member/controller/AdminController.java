package com.timcooki.jnuwiki.domain.member.controller;

import com.timcooki.jnuwiki.domain.docs.DTO.response.InfoEditResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.NewListReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.NewReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.EditListReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.EditReadResDTO;
import com.timcooki.jnuwiki.util.ApiUtils;
import com.timcooki.jnuwiki.domain.docsRequest.service.DocsRequestService;
import com.timcooki.jnuwiki.domain.member.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AdminController {
    private final DocsRequestService docsRequestService;
    private final AdminService adminService;

    // 문서 기본정보 수정 요청 목록 조회
    @GetMapping("/admin/requests/update")
    private ResponseEntity<?> getModifiedRequests(@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        EditListReadResDTO modifiedRequests = docsRequestService.getModifiedRequestList(pageable);
        return ResponseEntity.ok().body(ApiUtils.success(modifiedRequests));
    }

    // 새 문서 생성 요청 목록 조회
    @GetMapping("/admin/requests/new")
    private Object getCreatedRequests(@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        NewListReadResDTO createRequests = docsRequestService.getCreatedRequestList(pageable);
        return ApiUtils.success(createRequests);
    }

    // 문서 기본 정보 수정 요청 상세 조회
    @GetMapping("/admin/requests/update/{docs_request_id}")
    private Object getOneModifiedRequest(@PathVariable("docs_request_id") Long docsRequestId) {
        EditReadResDTO modifiedRequest = docsRequestService.getOneModifiedRequest(docsRequestId);
        return ApiUtils.success(modifiedRequest);
    }

    // 새 문서 신청 요청 상세 조회
    @GetMapping("/admin/requests/new/{docs_request_id}")
    private Object getOneCreatedRequest(@PathVariable("docs_request_id") Long docsRequestId) {
        NewReadResDTO modifiedRequest = docsRequestService.getOneCreatedRequest(docsRequestId);
        return ApiUtils.success(modifiedRequest);
    }

    // TODO - 예외처리 수정
    // 새 문서 생성 요청 승락
    @PostMapping("/admin/approve/new/{docs_request_id}")
    private ResponseEntity<?> approveCreateRequest(@PathVariable("docs_request_id") Long docsRequestId) {
        return ResponseEntity.ok(ApiUtils.success(adminService.approveNewDocs(docsRequestId)));

    }

    // 문서 수정 요청 승락
    @PostMapping("/admin/approve/update/{docs_request_id}")
    private ResponseEntity<?> approveModifiedRequest(@PathVariable("docs_request_id") Long docsRequestId) {
        InfoEditResDTO updatedDocs = adminService.updateDocsFromRequest(docsRequestId);
        return ResponseEntity.ok().body(ApiUtils.success(updatedDocs));
    }

    // 문서 요청 반려
    @PostMapping("/admin/reject/{docs_request_id}")
    private ResponseEntity<?> rejectRequest(@PathVariable("docs_request_id") Long docsRequestId) {
        adminService.rejectRequest(docsRequestId);
        return ResponseEntity.ok(ApiUtils.success("요청이 반려되었습니다."));
    }
}
