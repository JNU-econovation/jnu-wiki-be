package com.timcooki.jnuwiki.domain.member.controller;

import com.timcooki.jnuwiki.domain.member.DTO.*;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.domain.member.service.MemberService;
import com.timcooki.jnuwiki.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/members/login")
    public ResponseEntity<?> login(@RequestBody LoginReqDTO loginReqDTO){

        /*
        // TODO Dummy Data - fail1: 이메일 형식 오류
        if(loginReqDTO.getEmail().equals("fail1")){
            return ResponseEntity.status(400).body(ApiUtils.error("이메일 형식으로 작성해주세요.:"+loginReqDTO.getEmail(), HttpStatus.BAD_REQUEST));
        }
        // TODO Dummy Data - fail2: 비밀번호 형식 오류
        if(loginReqDTO.getPassword().equals("fail2")){
            return ResponseEntity.status(400).body(ApiUtils.error("비밀번호는 8~16자여야 하고 영문, 숫자, 특수문자가 포함되어야합니다.:"+loginReqDTO.getPassword(),HttpStatus.BAD_REQUEST));
        }
        // TODO Dummy Data - fail3: 인증 오류
        if(loginReqDTO.getEmail().equals("fail3")){
            return ResponseEntity.status(401).body(ApiUtils.error("이메일과 비밀번호를 확인해주세요.", HttpStatus.BAD_REQUEST));
        }
         */


        return memberService.login(loginReqDTO.email(), loginReqDTO.password());

    }

    @PostMapping("/members/join")
    public ResponseEntity<?> join(@RequestBody JoinReqDTO joinReqDTO){

        /*
        // TODO Dummy Data - fail1: 이메일 형식 오류
        if(joinReqDTO.getEmail().equals("fail1")){
            return ResponseEntity.status(400).body(ApiUtils.error("이메일 형식으로 작성해주세요.:"+joinReqDTO.getEmail(), HttpStatus.BAD_REQUEST));
        }
        // TODO Dummy Data - fail2: 비밀번호 형식 오류
        if(joinReqDTO.getPassword().equals("fail2")){
            return ResponseEntity.status(400).body(ApiUtils.error("비밀번호는 8~16자여야 하고 영문, 숫자, 특수문자가 포함되어야합니다.:"+joinReqDTO.getPassword(),HttpStatus.BAD_REQUEST));
        }
        // TODO Dummy Data - fail3: 동일한 이메일 입력
        if(joinReqDTO.getEmail().equals("fail3")){
            return ResponseEntity.status(400).body(ApiUtils.error("동일한 이메일이 존재합니다:"+joinReqDTO.getEmail(),HttpStatus.BAD_REQUEST));
        }

         */


        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    // TODO AuthenticationPrincipal - SecurityContextHolder/Authentication도 고려
    @GetMapping("/members/info")
    public ResponseEntity<?> info(@AuthenticationPrincipal Member member){

        // TODO Dummy Data - fail1: 잘못된 인증
        if(member.getMemberId()!=1){
           return ResponseEntity.status(401).body(ApiUtils.error("잘못된 인증입니다.", HttpStatus.UNAUTHORIZED));
        }

        InfoResDTO members = new InfoResDTO(member.getMemberId(), member.getNickName(), member.getPassword());
        return ResponseEntity.ok().body(ApiUtils.success(members));
    }

    // TODO AuthenticationPrincipal - SecurityContextHolder/Authentication도 고려
    @PostMapping("/members/modify/change")
    public ResponseEntity<?> modifyInfo(@AuthenticationPrincipal Member member,
                                        @RequestBody ModifyMemberInfoReqDTO modifyMemberInfoReqDTO){

        // findById로 member 찾기.

        // TODO Dummy Data - fail1: 400 중복된 닉네임
        if(modifyMemberInfoReqDTO.nickname().equals("fail1")){
            return ResponseEntity.status(400).body(ApiUtils.error("중복된 닉네임 입니다.:"+modifyMemberInfoReqDTO.getNickname(), HttpStatus.BAD_REQUEST));
        }

        // TODO Dummy Data - fail2: 400 비밀번호 형식 오류
        if(modifyMemberInfoReqDTO.password().equals("fail2")){
            return ResponseEntity.status(400).body(ApiUtils.error("비밀번호는 8~16자여야 하고 영문, 숫자, 특수문자가 포함되어야합니다.:"+modifyMemberInfoReqDTO.getPassword(), HttpStatus.BAD_REQUEST));
        }

        // TODO Dummy Data - fail3: 401 인증 오류


        // 수정사항 update / Param : nickname, password


        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
