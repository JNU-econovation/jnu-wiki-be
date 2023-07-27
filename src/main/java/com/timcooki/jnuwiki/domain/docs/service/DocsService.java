package com.timcooki.jnuwiki.domain.docs.service;

import com.timcooki.jnuwiki.domain.docs.DTO.request.ContentEditReqDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.response.ContentEditResDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.response.ListReadResDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.response.ReadResDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.response.SearchReadResDTO;
import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docs.mapper.DocsMapper;
import com.timcooki.jnuwiki.domain.docs.repository.DocsRepository;
import com.timcooki.jnuwiki.util.errors.exception.Exception404;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocsService {
    private final DocsRepository docsRepository;
    public List<ListReadResDTO> getDocsList(Pageable pageable) {
        List<Docs> list = docsRepository.findAll(pageable).getContent();
        if(list == null || list.isEmpty()){
            throw new Exception404("문서 목록이 존재하지 않습니다.");
        }

        List<ListReadResDTO> docsDTOList = list.stream()
                .map(docs -> new ListReadResDTO(
                        docs.getDocsId(),
                        docs.getDocsName(),
                        docs.getDocsCategory(),
                        docs.getDocsLocation(),
                        docs.getDocsContent(),
                        docs.getCreatedBy().getNickName(),
                        docs.getCreatedAt(),
                        docs.getModifiedAt()))
                .collect(Collectors.toList());

        return docsDTOList;
    }

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
                docs.getModifiedAt()
        );
    }

    public ReadResDTO getOneDocs(Long docsId) {
        Docs docs = docsRepository.findById(docsId).orElseThrow(
                () -> new Exception404("존재하지 않는 문서입니다.")
        );

        return new ReadResDTO(
                docs.getDocsId(),
                docs.getDocsName(),
                docs.getDocsCategory(),
                docs.getDocsLocation(),
                docs.getDocsContent(),
                docs.getCreatedBy().getNickName(),
                docs.getCreatedAt()
        );
    }

    public List<SearchReadResDTO> searchLike(String search) {
        List<Docs> docsList = docsRepository.searchLike(search);
        if(docsList!= null && !docsList.isEmpty()){
            DocsMapper mapper = Mappers.getMapper(DocsMapper.class);

            List<SearchReadResDTO> res = docsList.stream().map((docs -> mapper.entityToDTO(docs, docs.getCreatedBy().getNickName()))).toList();

            return res;
        }
        else {
            throw new Exception404("요청결과가 존재하지 않습니다.");
        }

    }
}
