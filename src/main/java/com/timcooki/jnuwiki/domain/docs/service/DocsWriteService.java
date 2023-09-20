package com.timcooki.jnuwiki.domain.docs.service;

import com.timcooki.jnuwiki.domain.docs.DTO.request.ContentEditReqDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.request.FindAllReqDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.response.*;
import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docs.mapper.DocsMapper;
import com.timcooki.jnuwiki.domain.docs.repository.DocsRepository;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.domain.member.repository.MemberRepository;
import com.timcooki.jnuwiki.domain.scrap.entity.Scrap;
import com.timcooki.jnuwiki.domain.scrap.entity.ScrapId;
import com.timcooki.jnuwiki.domain.scrap.repository.ScrapRepository;
import com.timcooki.jnuwiki.util.errors.exception.Exception404;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
