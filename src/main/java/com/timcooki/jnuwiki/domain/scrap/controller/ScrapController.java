package com.timcooki.jnuwiki.domain.scrap.controller;

import com.timcooki.jnuwiki.domain.scrap.DTO.request.DeleteScrapReqDTO;
import com.timcooki.jnuwiki.domain.scrap.DTO.request.NewScrapReqDTO;
import com.timcooki.jnuwiki.domain.scrap.service.ScrapWriteService;
import com.timcooki.jnuwiki.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scrap")
@RequiredArgsConstructor
public class ScrapController {

    private final ScrapWriteService scrapWriteService;

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody NewScrapReqDTO newScrapReqDTO){
        scrapWriteService.create(newScrapReqDTO);
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    @DeleteMapping("/")
    public ResponseEntity<?> delete(@RequestBody DeleteScrapReqDTO deleteScrapReqDTO){
        scrapWriteService.delete(deleteScrapReqDTO);
        return ResponseEntity.ok(ApiUtils.success(null));
    }
}
