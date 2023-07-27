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

        return memberService.join(joinReqDTO);
    }

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


        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
