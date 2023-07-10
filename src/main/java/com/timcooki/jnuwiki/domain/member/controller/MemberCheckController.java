package com.timcooki.jnuwiki.domain.member.controller;

import com.timcooki.jnuwiki.domain.member.DTO.CheckEmailReqDTO;
import com.timcooki.jnuwiki.domain.member.DTO.CheckNicknameReqDTO;
import com.timcooki.jnuwiki.util.ApiUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MemberCheckController {

    // 닉네임 중복 검사
    @PostMapping("/members/check/nickname")
    public ResponseEntity<?> checkNickname(@RequestBody CheckNicknameReqDTO checkReqDTO){

        // TODO Dummy data 추후 변경 - 닉네임 중복시
        if(checkReqDTO.getNickname().equals("error")){
            return ResponseEntity.status(400).body(ApiUtils.error("동일한 닉네임이 존재합니다.:"+checkReqDTO.getNickname(), HttpStatus.BAD_REQUEST));
        }

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    // 이메일 중복 검사
    @PostMapping("/members/check/email")
    public ResponseEntity<?> checkEmail(@RequestBody CheckEmailReqDTO checkEmailReqDTO){

        // TODO Dummy data 추후 변경 - 이메일 중복시
        if(checkEmailReqDTO.getEmail().equals("error")){
            return ResponseEntity.status(400).body(ApiUtils.error("동일한 이메일이 존재합니다.:" + checkEmailReqDTO.getEmail(), HttpStatus.BAD_REQUEST));
        }
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

}
