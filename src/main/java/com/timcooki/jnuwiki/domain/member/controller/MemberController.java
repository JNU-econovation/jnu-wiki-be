package com.timcooki.jnuwiki.domain.member.controller;

import com.timcooki.jnuwiki.domain.member.DTO.request.*;
import com.timcooki.jnuwiki.domain.member.DTO.response.AccessTokenResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.LoginResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.ReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.WrapLoginResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.WrapAccessTokenResDTO;
import com.timcooki.jnuwiki.domain.member.service.MemberReadService;
import com.timcooki.jnuwiki.domain.member.service.MemberWriteService;
import com.timcooki.jnuwiki.domain.security.service.RefreshTokenService;
import com.timcooki.jnuwiki.util.ApiResult;
import com.timcooki.jnuwiki.util.ApiUtils;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResult<LoginResDTO>> login(HttpServletResponse response,
                                                        @RequestBody @Valid LoginReqDTO loginReqDTO) {
        WrapLoginResDTO wrapLoginResDTO = memberWriteService.login(response, loginReqDTO);
        return ResponseEntity.ok()
                .headers(wrapLoginResDTO.headers())
                .body(ApiUtils.success(wrapLoginResDTO.body()));
    }

    // access token 재발급
    @PostMapping("/access-token")
    public ResponseEntity<ApiResult<AccessTokenResDTO>> refreshToken(@CookieValue(value = "refresh-token") String refreshToken) {
        WrapAccessTokenResDTO wrapAccessTokenResDTO = refreshTokenService.renewAccessToken(refreshToken);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, wrapAccessTokenResDTO.accessToken())
                .body(ApiUtils.success(wrapAccessTokenResDTO.accessTokenResDTO()));
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid JoinReqDTO joinReqDTO) {
        memberWriteService.join(joinReqDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiUtils.success(null));
    }

    @GetMapping("/info")
    public ResponseEntity<?> info() {
        ReadResDTO memberInfo = memberReadService.getInfo();
        return ResponseEntity.ok(ApiUtils.success(memberInfo));
    }

    @PutMapping("/nickname")
    public ResponseEntity<?> editMemberEmail(@RequestBody EditNicknameReqDTO newNickname) {
        memberWriteService.editMemberNickname(newNickname);
        return ResponseEntity.ok(ApiUtils.success("닉네임이 변경되었습니다." + newNickname.nickname()));
    }

    @PutMapping("/password")
    public ResponseEntity<?> editMemberPassword(@RequestBody EditPasswordReqDTO newPassword) {
        memberWriteService.editMemberPassword(newPassword);
        return ResponseEntity.ok(ApiUtils.success("비밀번호가 변경되었습니다." + newPassword.password()));
    }

    @GetMapping("/scrap")
    public ResponseEntity<?> getScrappedDocsList(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(ApiUtils.success(memberReadService.getScrappedDocs(pageable)));
    }
}
