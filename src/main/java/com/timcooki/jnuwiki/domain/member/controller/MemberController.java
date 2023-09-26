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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberWriteService memberWriteService;
    private final MemberReadService memberReadService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReqDTO loginReqDTO) {
        return ResponseEntity.ok(memberWriteService.login(loginReqDTO));
    }

    // refresh token 재발급
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestHeader(value = "set-cookie") String refreshToken) {
        try {
            String accessToken = refreshTokenService.renewToken(refreshToken);
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, accessToken)
                    .body(ApiUtils.success("토큰 재발급 성공"));

        } catch (Exception e) {
            return ResponseEntity.status(401).body(ApiUtils.error(e.getMessage(), HttpStatus.UNAUTHORIZED));
        }
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody JoinReqDTO joinReqDTO) {
        memberWriteService.join(joinReqDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiUtils.success(null));
    }

    @GetMapping("/info")
    public ResponseEntity<?> info() {
        ReadResDTO memberInfo = memberReadService.getInfo();
        return ResponseEntity.ok(ApiUtils.success(memberInfo));
    }

    @PostMapping("/modify/change")
    public ResponseEntity<?> modifyInfo(@RequestBody EditReqDTO editReqDTO) {
        memberWriteService.editInfo(editReqDTO);
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    @GetMapping("/scrap")
    public ResponseEntity<?> getScrappedDocsList(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(ApiUtils.success(memberReadService.getScrappedDocs(pageable)));
    }
}

