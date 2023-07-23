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
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class DocsController {
    private final DocsService docsService;

    // 모든 문서 조회 -
    @GetMapping("/docs")
    public ResponseEntity<?> docsFindAll(@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){
        Page<ListReadResDTO> docsList = docsService.getDocsList(pageable);
        return ResponseEntity.ok().body(ApiUtils.success(docsList));
    }

    // 문서 수정
    @PutMapping("/docs/{docs_id}")
    public ResponseEntity<?> modifyDocs(@PathVariable Long docs_id,
                                        @RequestBody ContentEditReqDTO contentEditReqDTO,
                                        @AuthenticationPrincipal Member member){
        // 권한 확인


        ContentEditResDTO contentEditDocs = docsService.updateDocs(docs_id, contentEditReqDTO);

        return ResponseEntity.ok().body(ApiUtils.success(contentEditDocs));
    }

    @GetMapping("/docs/{docs_id}")
    public ResponseEntity<?> docsFindOne(@PathVariable Long docs_id){
        ReadResDTO readDTO = docsService.getOneDocs(docs_id);
        return ResponseEntity.ok().body(ApiUtils.success(readDTO));
    }

    @GetMapping("/docs/search")
    public ResponseEntity<?> docsSearch(@RequestParam(value = "search") String search){

        return ResponseEntity.ok(ApiUtils.success(docsService.searchLike(search)));
    }
}
