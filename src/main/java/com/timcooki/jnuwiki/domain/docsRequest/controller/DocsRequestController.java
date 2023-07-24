package com.timcooki.jnuwiki.domain.docsRequest.controller;


import com.timcooki.jnuwiki.domain.docsRequest.dto.response.NewWriteResDTO;
import com.timcooki.jnuwiki.util.ApiUtils;
import com.timcooki.jnuwiki.domain.docsRequest.dto.request.NewWriteReqDTO;
import com.timcooki.jnuwiki.domain.docsRequest.dto.request.EditWriteReqDTO;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequest;
import com.timcooki.jnuwiki.domain.docsRequest.service.DocsRequestService;
import com.timcooki.jnuwiki.domain.member.service.MemberService;
import com.timcooki.jnuwiki.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@RestController
@RequestMapping("/requests")
public class DocsRequestController {
    private final DocsRequestService docsRequestService;
    private final MemberService memberService;

    // 문서 수정 요청 작성
    @PostMapping("/update")
    public ResponseEntity<?> writeModifiedRequest(@AuthenticationPrincipal UserDetails userDetails, @RequestBody EditWriteReqDTO modifiedRequestWriteDto) {
        // 권한 확인
        ResponseEntity<?> checkAuthorization = checkAuthorization(userDetails);
        if (checkAuthorization != null) return checkAuthorization;

        // 요청 저장
        docsRequestService.createModifiedRequest(modifiedRequestWriteDto);

        // 요청 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiUtils.success(new NewWriteResDTO("요청이 저장되었습니다.")));
    }

    // 문서 생성 요청 작성
    @PostMapping("/new")
    public ResponseEntity<?> writeCreateRequest(@AuthenticationPrincipal UserDetails userDetails, @RequestBody NewWriteReqDTO newWriteReqDTO) {
        // TODO - CustomUserDetails | findByEmail


        // 문서 저장
        docsRequestService.createNewDocsRequest(userDetails, newWriteReqDTO);

        // 요청 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiUtils.success(new NewWriteResDTO("요청이 저장되었습니다.")));
    }

    // 권한 확인 메서드
    private ResponseEntity<?> checkAuthorization(UserDetails userDetails) {

        // 회원 가입 일수 확인
        // TODO: UserDetails 구현 완료되고 나서 getCreatedAt() 에러 해결되는지 확인
        try{
            docsRequestService.checkMemberDuration(userDetails);
        }catch (Exception e){
            return ResponseEntity.status(403).body(ApiUtils.error(e.getMessage(), HttpStatus.FORBIDDEN));
        }
        return null;
    }
}
