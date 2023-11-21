package com.timcooki.jnuwiki.domain.member.controller;

import com.timcooki.jnuwiki.domain.member.DTO.request.CheckEmailReqDTO;
import com.timcooki.jnuwiki.domain.member.DTO.request.CheckNicknameReqDTO;
import com.timcooki.jnuwiki.domain.member.service.MemberCheckService;
import com.timcooki.jnuwiki.domain.member.service.MemberWriteService;
import com.timcooki.jnuwiki.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberCheckController {

    private final MemberCheckService memberCheckService;

    // 닉네임 중복 검사
    @PostMapping("/members/check/nickname")
    public ResponseEntity<?> checkNickname(@RequestBody CheckNicknameReqDTO checkReqDTO) {
        memberCheckService.checkNickName(checkReqDTO);
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    // 이메일 중복 검사
    @PostMapping("/members/check/email")
    public ResponseEntity<?> checkEmail(@RequestBody CheckEmailReqDTO checkEmailReqDTO) {
        memberCheckService.checkEmail(checkEmailReqDTO);
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
