package com.timcooki.jnuwiki.domain.scrap.service;

import com.timcooki.jnuwiki.domain.scrap.DTO.request.DeleteScrapReqDTO;
import com.timcooki.jnuwiki.domain.scrap.DTO.request.NewScrapReqDTO;
import com.timcooki.jnuwiki.domain.scrap.entity.Scrap;
import com.timcooki.jnuwiki.domain.scrap.repository.ScrapRepository;
import com.timcooki.jnuwiki.util.ApiResult;
import com.timcooki.jnuwiki.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScrapWriteService {

    private final ScrapRepository scrapRepository;

    @Transactional
    public void create(NewScrapReqDTO newScrapReqDTO){
        Scrap scrap = dtoToScrap(newScrapReqDTO.memberId(), newScrapReqDTO.docsId());
        scrapRepository.save(scrap);
    }

    @Transactional
    public void delete(DeleteScrapReqDTO deleteScrapReqDTO){
        Scrap scrap = dtoToScrap(deleteScrapReqDTO.memberId(), deleteScrapReqDTO.docsId());
        scrapRepository.delete(scrap);
    }

    private static Scrap dtoToScrap(Long memberId, Long docsId) {
        return Scrap.builder()
                .memberId(memberId)
                .docsId(docsId)
                .build();
    }

}
