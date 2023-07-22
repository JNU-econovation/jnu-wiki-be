package com.timcooki.jnuwiki.domain.docs.service;

import com.timcooki.jnuwiki.domain.docs.DTO.request.ContentEditReqDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.response.ContentEditResDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.response.ListReadResDTO;
import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docs.repository.DocsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocsService {
    private final DocsRepository docsRepository;
    public Page<ListReadResDTO> getDocsList(Pageable pageable) {
        Page<Docs> list = docsRepository.findAll(pageable);

        List<ListReadResDTO> docsDTOList = list.stream()
                .map(docs -> new ListReadResDTO(
                        docs.getDocsId(),
                        docs.getDocsName(),
                        docs.getDocsCategory(),
                        docs.getDocsLocation(),
                        docs.getDocsContent(),
                        docs.getCreatedBy(),
                        docs.getCreatedAt()))
                .collect(Collectors.toList());

        return new PageImpl<>(docsDTOList);
    }

    public ContentEditResDTO updateDocs(Long docsId, ContentEditReqDTO contentEditReqDTO) {
    }
}
