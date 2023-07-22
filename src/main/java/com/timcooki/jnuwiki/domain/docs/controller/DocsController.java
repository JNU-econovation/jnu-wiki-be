package com.timcooki.jnuwiki.domain.docs.controller;

import com.timcooki.jnuwiki.domain.docs.DTO.request.ContentEditReqDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.response.ContentEditResDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.response.ListReadResDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.response.ReadResDTO;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.util.ApiUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@Slf4j
public class DocsController {

    // 모든 문서 조회 -
    // TODO 추가 - pagenation
    @GetMapping("/docs")
    public ResponseEntity<?> docsFindAll(){

        // TODO Dummy Data - fail1: 400 BAD_REQUEST / 어떤 경우에 400이 뜨는지 체크


        return ResponseEntity.ok().body(ApiUtils.success(new ListReadResDTO(1L, "팬도로시", "CAFE", null, "내용", "cookie", null)));
    }

    // 문서 수정
    @PutMapping("/docs/{docs_id}")
    public ResponseEntity<?> modifyDocs(@PathVariable Long docs_id,
                                        @RequestBody ContentEditReqDTO contentEditReqDTO,
                                        @AuthenticationPrincipal Member member){

        // TODO Dummy Data - fail1 : 400/잘못된 조회
        // TODO Dummy Data - fail2 : 401/인증 오류
        // TODO Dummy Data - fail3 : 403/회원가입 15일 미만

        // TODO Dummy Data - success : 200/문서 수정

        return ResponseEntity.ok().body(ApiUtils.success(new ContentEditResDTO(
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

        return ResponseEntity.ok(ApiUtils.success(new ReadResDTO(
                13L,
                "팬도로시",
                "CAFE",
                null,
                "마크다운 내용",
                "cookie",
                LocalDateTime.now()
        )));
    }
    
}
