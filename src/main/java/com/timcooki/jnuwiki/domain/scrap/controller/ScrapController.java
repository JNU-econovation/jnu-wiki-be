package com.timcooki.jnuwiki.domain.scrap.controller;

import com.timcooki.jnuwiki.domain.scrap.DTO.request.DeleteScrapReqDTO;
import com.timcooki.jnuwiki.domain.scrap.DTO.request.NewScrapReqDTO;
import com.timcooki.jnuwiki.domain.scrap.service.ScrapWriteService;
import com.timcooki.jnuwiki.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScrapController {

    private final ScrapWriteService scrapWriteService;

    @PostMapping("/scrap/create")
    public ResponseEntity<?> create(@RequestBody NewScrapReqDTO newScrapReqDTO){
        return ResponseEntity.ok(ApiUtils.success(scrapWriteService.create(newScrapReqDTO)));
    }

    @DeleteMapping("/scrap")
    public ResponseEntity<?> delete(@RequestBody DeleteScrapReqDTO deleteScrapReqDTO){
        return ResponseEntity.ok(ApiUtils.success(scrapWriteService.delete(deleteScrapReqDTO)));
    }
}
