package com.timcooki.jnuwiki.domain.docs.controller;

import com.timcooki.jnuwiki.domain.docs.DTO.*;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.util.ApiUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class DocsController {

    // 모든 문서 조회 -
    // TODO 추가 - pagenation
    @GetMapping("/docs")
    public ResponseEntity<?> docsFindAll(){

        // TODO Dummy Data - fail1: 400 BAD_REQUEST / 어떤 경우에 400이 뜨는지 체크


        return ResponseEntity.ok().body(ApiUtils.success(new DocsInfoResDTO(1L, "팬도로시", "CAFE", null, "내용", "cookie", null)));
    }

    // 문서 수정
    @PutMapping("/docs/{docs_id}")
    public ResponseEntity<?> modifyDocs(@PathVariable Long docs_id,
                                        @RequestBody ModifyDocsReqDTO modifyDocsReqDTO,
                                        @AuthenticationPrincipal Member member){

        // TODO Dummy Data - fail1 : 400/잘못된 조회
        // TODO Dummy Data - fail2 : 401/인증 오류
        // TODO Dummy Data - fail3 : 403/회원가입 15일 미만

        // TODO Dummy Data - success : 200/문서 수정

        return ResponseEntity.ok().body(ApiUtils.success(new ModifyDocsResDTO(
                2L,
                "팀 쿠키",
                "cookie",
                LocalDateTime.now()
        )));
    }

    @GetMapping("/docs/{docs_id}")
    public ResponseEntity<?> docsFindOne(@PathVariable Long docs_id){

        // TODO Dummy Data - fail1 : 400/잘못된 조회
        // TODO Dummy Data - fail2 : 404/존재하지 않는 문서

        // TODO Dummy Data - success : 성공한 경우

        return ResponseEntity.ok(ApiUtils.success(new DocsFindOneResDTO(
                13L,
                "팬도로시",
                "CAFE",
                null,
                "마크다운 내용",
                "cookie",
                LocalDateTime.now()
        )));
    }

    @PatchMapping("/docs/{docs_id}")
    public ResponseEntity<?> docsUpdate(@PathVariable String docs_id,
                                        @RequestBody DocsUpdateReqDTO docsUpdateReqDTO){

        return ResponseEntity.ok(ApiUtils.success(new DocsUpdateResDTO(
                13L,
                "팬도로시",
                "CAFE",
                new ArrayList<>(List.of(1,3)),
                "Content",
                "cookie",
                LocalDateTime.now()
        )));

    }

    @PostMapping("/docs")
    public ResponseEntity<?> docsCreate(@RequestBody DocsCreateReqDTO docsCreateReqDTO){

        return ResponseEntity.ok(ApiUtils.success(DocsCreateResDTO.builder()
                .docsId(13L)
                .docsName("팬도로시")
                .docsCategory("CAFE")
                .docsLocation(new ArrayList<>(List.of(1, 3)))
                .docsCreatedBy("cookie")
                .docsContent("content")
                .docsCreatedAt(LocalDateTime.now())
                .build()));
    }

}
