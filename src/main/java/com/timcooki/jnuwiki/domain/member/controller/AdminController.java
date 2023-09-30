package com.timcooki.jnuwiki.domain.member.controller;

import com.timcooki.jnuwiki.domain.docs.DTO.response.InfoEditResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.NewListReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.NewReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.EditListReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.EditReadResDTO;
import com.timcooki.jnuwiki.util.ApiUtils;
import com.timcooki.jnuwiki.domain.docsRequest.service.DocsRequestReadService;
import com.timcooki.jnuwiki.domain.member.service.AdminWriteService;
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
@RequestMapping("/admin")
public class AdminController {
    private final DocsRequestReadService docsRequestReadService;
    private final AdminWriteService adminWriteService;

    // 문서 기본정보 수정 요청 목록 조회
    @GetMapping("/requests/update")
    private ResponseEntity<?> getModifiedRequests(@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        EditListReadResDTO modifiedRequests = docsRequestReadService.getModifiedRequestList(pageable);
        return ResponseEntity.ok(ApiUtils.success(modifiedRequests));
    }

    // 새 문서 생성 요청 목록 조회
    @GetMapping("/requests/new")
    private ResponseEntity<?> getCreatedRequests(@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        NewListReadResDTO createRequests = docsRequestReadService.getCreatedRequestList(pageable);
        return ResponseEntity.ok(ApiUtils.success(createRequests));
    }

    // 문서 기본 정보 수정 요청 상세 조회
    @GetMapping("/requests/update/{docs_request_id}")
    private ResponseEntity<?> getOneModifiedRequest(@PathVariable("docs_request_id") Long docsRequestId) {
        EditReadResDTO modifiedRequest = docsRequestReadService.getOneModifiedRequest(docsRequestId);
        return ResponseEntity.ok(ApiUtils.success(modifiedRequest));
    }

    // 새 문서 신청 요청 상세 조회
    @GetMapping("/requests/new/{docs_request_id}")
    private ResponseEntity<?> getOneCreatedRequest(@PathVariable("docs_request_id") Long docsRequestId) {
        NewReadResDTO modifiedRequest = docsRequestReadService.getOneCreatedRequest(docsRequestId);
        return ResponseEntity.ok(ApiUtils.success(modifiedRequest));
    }

    // 새 문서 생성 요청 승락
    @PostMapping("/approve/new/{docs_request_id}")
    private ResponseEntity<?> approveCreateRequest(@PathVariable("docs_request_id") Long docsRequestId) {
        return ResponseEntity.ok(ApiUtils.success(adminWriteService.approveNewDocs(docsRequestId)));
    }

    // 문서 수정 요청 승락
    @PostMapping("/approve/update/{docs_request_id}")
    private ResponseEntity<?> approveModifiedRequest(@PathVariable("docs_request_id") Long docsRequestId) {
        InfoEditResDTO updatedDocs = adminWriteService.updateDocsFromRequest(docsRequestId);
        return ResponseEntity.ok(ApiUtils.success(updatedDocs));
    }

    // 문서 요청 반려
    @PostMapping("/reject/{docs_request_id}")
    private ResponseEntity<?> rejectRequest(@PathVariable("docs_request_id") Long docsRequestId) {
        adminWriteService.rejectRequest(docsRequestId);
        return ResponseEntity.ok(ApiUtils.success("요청이 반려되었습니다."));
    }
}
