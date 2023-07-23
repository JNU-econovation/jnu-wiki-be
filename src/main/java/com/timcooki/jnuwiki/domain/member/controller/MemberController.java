package com.timcooki.jnuwiki.domain.member.controller;

import com.timcooki.jnuwiki.domain.member.DTO.request.EditReqDTO;
import com.timcooki.jnuwiki.domain.member.DTO.request.JoinReqDTO;
import com.timcooki.jnuwiki.domain.member.DTO.request.LoginReqDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.InfoResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.ReadResDTO;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.domain.member.service.MemberService;
import com.timcooki.jnuwiki.domain.security.service.RefreshTokenService;
import com.timcooki.jnuwiki.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final RefreshTokenService refreshTokenService;

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


        return memberService.login(loginReqDTO);

    }

    // refresh token 재발급
    @PostMapping("/members/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestHeader(value = "set-cookie") String refreshToken){

        try{
            return refreshTokenService.renewToken(refreshToken);
        }catch (Exception e){
            return ResponseEntity.status(401).body(ApiUtils.error(e.getMessage(), HttpStatus.UNAUTHORIZED));
        }


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

        try{
            return memberService.join(joinReqDTO);
        }catch (Exception e){
            return ResponseEntity.status(500).body(ApiUtils.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        }

    }

    // TODO AuthenticationPrincipal - SecurityContextHolder/Authentication도 고려
    @GetMapping("/members/info")
    public ResponseEntity<?> info(@AuthenticationPrincipal UserDetails userDetails){
        ReadResDTO member = memberService.getInfo(userDetails);

        return ResponseEntity.ok().body(ApiUtils.success(member));
    }

    // TODO AuthenticationPrincipal - SecurityContextHolder/Authentication도 고려
    @PostMapping("/members/modify/change")
    public ResponseEntity<?> modifyInfo(@AuthenticationPrincipal UserDetails userDetails,
                                        @RequestBody EditReqDTO editReqDTO){

        memberService.editInfo(userDetails, editReqDTO);
/*
        // TODO Dummy Data - fail2: 400 비밀번호 형식 오류
        if(modifyMemberInfoReqDTO.password().equals("fail2")){
            return ResponseEntity.status(400).body(ApiUtils.error("비밀번호는 8~16자여야 하고 영문, 숫자, 특수문자가 포함되어야합니다.:"+modifyMemberInfoReqDTO.getPassword(), HttpStatus.BAD_REQUEST));
        }
 */
        // 수정사항 update / Param : nickname, password

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
