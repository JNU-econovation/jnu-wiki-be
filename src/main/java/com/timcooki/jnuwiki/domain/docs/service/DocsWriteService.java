package com.timcooki.jnuwiki.domain.docs.service;

import com.timcooki.jnuwiki.domain.docs.DTO.request.ContentEditReqDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.response.ContentEditResDTO;
import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docs.repository.DocsRepository;
import com.timcooki.jnuwiki.util.errors.exception.Exception404;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DocsWriteService {
    private final DocsRepository docsRepository;
    @Transactional
    public ContentEditResDTO updateDocs(Long docsId, ContentEditReqDTO contentEditReqDTO) {
        Docs docs = docsRepository.findById(docsId).orElseThrow(
                () -> new Exception404("존재하지 않는 문서입니다.")
        );
        // TODO - 가입일 15일 체크 추가

        docs.updateContent(contentEditReqDTO.docsContent());

        return new ContentEditResDTO(
                docs.getDocsId(),
                docs.getDocsContent(),
                docs.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"))
        );
    }
}
