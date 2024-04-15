package com.timcooki.jnuwiki.domain.docsRequest.controller;

import com.timcooki.jnuwiki.domain.docsRequest.dto.DocsMessage;
import com.timcooki.jnuwiki.domain.docsRequest.service.DocsRequestReadService;
import com.timcooki.jnuwiki.domain.docsRequest.service.DocsRequestWriteService;
import com.timcooki.jnuwiki.util.ApiResult;
import com.timcooki.jnuwiki.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class DocsRequestWebSocketController {
    private final DocsRequestWriteService docsRequestWriteService;
    private final DocsRequestReadService docsRequestReadService;

    @MessageMapping("/info")
    @SendToUser("/queue/info")
    public ApiResult<String> info(@Payload DocsMessage message) throws Exception{

        docsRequestWriteService.createDocsStatus(message);

        return ApiUtils.success("success");
    }


    @GetMapping("/requests/docs/status/{docsId}")
    public ResponseEntity<?> getDocsStatus(@PathVariable Long docsId){

        return ResponseEntity.ok(ApiUtils.success(docsRequestReadService.getDocsStatus(docsId)));
    }

}
