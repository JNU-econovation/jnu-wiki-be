package com.timcooki.jnuwiki.domain.docs.controller;

import com.timcooki.jnuwiki.domain.docs.DTO.request.ContentEditReqDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.response.ContentEditResDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.response.ListReadResDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.response.ReadResDTO;
import com.timcooki.jnuwiki.domain.docs.service.DocsService;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class DocsController {
    private final DocsService docsService;

    // 모든 문서 조회 - 최근 수정된 순으로
    @GetMapping("/docs")
    public ResponseEntity<?> docsFindAll(@AuthenticationPrincipal UserDetails userDetails, @PageableDefault(size = 50, sort = "modifiedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        if (userDetails != null) {
            return ResponseEntity.ok().body(ApiUtils.success(docsService.getDocsList(userDetails.getUsername(), pageable)));
        }
        return ResponseEntity.ok().body(ApiUtils.success(docsService.getDocsList(null, pageable)));
    }

    // 문서 수정
    @PutMapping("/docs/{docs_id}")
    public ResponseEntity<?> modifyDocs(@PathVariable Long docs_id, @RequestBody ContentEditReqDTO contentEditReqDTO) {
        return ResponseEntity.ok().body(ApiUtils.success(docsService.updateDocs(docs_id, contentEditReqDTO)));
    }

    @GetMapping("/docs/{docs_id}")
    public ResponseEntity<?> docsFindOne(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long docs_id) {
        if (userDetails != null) {
            return ResponseEntity.ok().body(ApiUtils.success(docsService.getOneDocs(userDetails.getUsername(), docs_id)));
        }
        return ResponseEntity.ok().body(ApiUtils.success(docsService.getOneDocs(null, docs_id)));
    }

    @GetMapping("/docs/search")
    public ResponseEntity<?> docsSearch(@RequestParam(value = "search") String search) {

        return ResponseEntity.ok(ApiUtils.success(docsService.searchLike(search)));
    }
}
