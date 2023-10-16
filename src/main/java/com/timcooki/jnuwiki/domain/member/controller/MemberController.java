package com.timcooki.jnuwiki.domain.member.controller;

import com.timcooki.jnuwiki.domain.member.DTO.request.*;
import com.timcooki.jnuwiki.domain.member.DTO.response.ReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.WrapLoginResDTO;
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
    public ResponseEntity<?> login(@RequestBody LoginReqDTO loginReqDTO){
        WrapLoginResDTO<?> dto = memberWriteService.login(loginReqDTO);

        return ResponseEntity.status(dto.status()).headers(dto.headers()).body(dto.body());
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

    // TODO: 분리한 API 전달 후 제거
    @PostMapping("/modify/change")
    public ResponseEntity<?> modifyInfo(@RequestBody EditReqDTO editReqDTO) {
        memberWriteService.editInfo(editReqDTO);
        return ResponseEntity.ok(ApiUtils.success(null));
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
