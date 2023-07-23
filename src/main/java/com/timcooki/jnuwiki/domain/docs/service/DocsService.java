package com.timcooki.jnuwiki.domain.docs.service;

import com.timcooki.jnuwiki.domain.docs.DTO.request.ContentEditReqDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.response.ContentEditResDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.response.ListReadResDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.response.ReadResDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.response.SearchReadResDTO;
import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docs.mapper.DocsMapper;
import com.timcooki.jnuwiki.domain.docs.repository.DocsRepository;
import com.timcooki.jnuwiki.domain.docsRequest.mapper.DocsRequestMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
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
        Docs docs = docsRepository.findById(docsId).orElseThrow(
                () -> new RuntimeException("존재하지 않는 문서입니다.")
        );

        docs.updateContent(contentEditReqDTO.docsContent());

        return new ContentEditResDTO(
                docs.getDocsId(),
                docs.getDocsContent(),
                docs.getModifiedAt()
        );
    }

    public ReadResDTO getOneDocs(Long docsId) {
        Docs docs = docsRepository.findById(docsId).orElseThrow(
                () -> new RuntimeException("존재하지 않는 문서입니다.")
        );

        return new ReadResDTO(
                docs.getDocsId(),
                docs.getDocsName(),
                docs.getDocsCategory(),
                docs.getDocsLocation(),
                docs.getDocsContent(),
                docs.getCreatedBy(),
                docs.getCreatedAt()
        );
    }

    public List<SearchReadResDTO> searchLike(String search) {
        List<Docs> docsList = docsRepository.searchLike(search);

        DocsMapper mapper = Mappers.getMapper(DocsMapper.class);

        List<SearchReadResDTO> res = docsList.stream().map(mapper::entityToDTO).toList();

        return res;

    }
}
