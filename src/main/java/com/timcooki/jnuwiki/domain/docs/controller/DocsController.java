package com.timcooki.jnuwiki.domain.docs.controller;

import com.timcooki.jnuwiki.domain.docs.DTO.request.ContentEditReqDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.request.FindAllReqDTO;
import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docs.service.DocsReadService;
import com.timcooki.jnuwiki.domain.docs.service.DocsWriteService;
import com.timcooki.jnuwiki.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
public class DocsController {
    private final DocsReadService docsReadService;
    private final DocsWriteService docsWriteService;

    // 모든 문서 조회 - 최근 수정된 순으로
    // 좌표 사이로
    @GetMapping("/docs")
    public ResponseEntity<?> docsFindAll(@PageableDefault(size = 50, sort = "modifiedAt", direction = Sort.Direction.DESC) Pageable pageable
            , FindAllReqDTO findAllReqDTO) {

        return ResponseEntity.ok().body(ApiUtils.success(docsReadService.getDocsList(pageable, findAllReqDTO)));
    }

    // 문서 수정
    @PutMapping("/docs/{docs_id}")
    public ResponseEntity<?> modifyDocs(@PathVariable Long docs_id, @RequestBody ContentEditReqDTO contentEditReqDTO) {
        return ResponseEntity.ok().body(ApiUtils.success(docsWriteService.updateDocs(docs_id, contentEditReqDTO)));
    }

    @GetMapping("/docs/{docs_id}")
    public ResponseEntity<?> docsFindOne(@PathVariable Long docs_id) {
        return ResponseEntity.ok().body(ApiUtils.success(docsReadService.getOneDocs(docs_id)));
    }

    @GetMapping("/docs/search")
    public ResponseEntity<?> docsSearch(@RequestParam(value = "search") String search) {

        return ResponseEntity.ok(ApiUtils.success(docsReadService.searchLike(search)));
    }
}
