package com.timcooki.jnuwiki.domain.member.controller;

import com.timcooki.jnuwiki.domain.member.DTO.request.EditReqDTO;
import com.timcooki.jnuwiki.domain.member.DTO.request.JoinReqDTO;
import com.timcooki.jnuwiki.domain.member.DTO.request.LoginReqDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.ReadResDTO;
import com.timcooki.jnuwiki.domain.member.service.MemberReadService;
import com.timcooki.jnuwiki.domain.member.service.MemberWriteService;
import com.timcooki.jnuwiki.domain.security.service.RefreshTokenService;
import com.timcooki.jnuwiki.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberWriteService memberWriteService;
    private final MemberReadService memberReadService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReqDTO loginReqDTO){
        return ResponseEntity.ok(memberWriteService.login(loginReqDTO));
    }

    // refresh token 재발급
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestHeader(value = "set-cookie") String refreshToken){
        try{
            return ResponseEntity.ok(refreshTokenService.renewToken(refreshToken));
        }catch (Exception e){
            return ResponseEntity.status(401).body(ApiUtils.error(e.getMessage(), HttpStatus.UNAUTHORIZED));
        }
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody JoinReqDTO joinReqDTO){
        return ResponseEntity.ok(memberWriteService.join(joinReqDTO));
    }

    @GetMapping("/info")
    public ResponseEntity<?> info(@AuthenticationPrincipal UserDetails userDetails){
        ReadResDTO member = memberReadService.getInfo(userDetails);
        return ResponseEntity.ok(ApiUtils.success(member));
    }

    @PostMapping("/modify/change")
    public ResponseEntity<?> modifyInfo(@RequestBody EditReqDTO editReqDTO){
        memberWriteService.editInfo(editReqDTO);
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    @GetMapping("/scrap")
    public ResponseEntity<?> getScrappedDocsList(@AuthenticationPrincipal UserDetails userDetails, @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(ApiUtils.success(memberReadService.getScrappedDocs(userDetails, pageable)));
    }
}
