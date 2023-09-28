package com.timcooki.jnuwiki.domain.docsRequest.controller;


import com.timcooki.jnuwiki.domain.docsRequest.dto.response.NewWriteResDTO;
import com.timcooki.jnuwiki.domain.docsRequest.service.DocsRequestWriteService;
import com.timcooki.jnuwiki.util.ApiUtils;
import com.timcooki.jnuwiki.domain.docsRequest.dto.request.NewWriteReqDTO;
import com.timcooki.jnuwiki.domain.docsRequest.dto.request.EditWriteReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/requests")
public class DocsRequestController {
    private final DocsRequestWriteService writeService;

    // 문서 수정 요청 작성
    @PostMapping("/update")
    public ResponseEntity<?> writeModifiedRequest(@RequestBody EditWriteReqDTO modifiedRequestWriteDto) {
        writeService.createModifiedRequest(modifiedRequestWriteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiUtils.success(new NewWriteResDTO("요청이 저장되었습니다.")));
    }

    // 문서 생성 요청 작성
    @PostMapping("/new")
    public ResponseEntity<?> writeCreateRequest(@RequestBody NewWriteReqDTO newWriteReqDTO) {
        writeService.createNewDocsRequest(newWriteReqDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiUtils.success(new NewWriteResDTO("요청이 저장되었습니다.")));
    }
}
