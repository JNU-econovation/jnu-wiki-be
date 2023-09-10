package com.timcooki.jnuwiki.domain.docsRequest.service;

import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docs.repository.DocsRepository;
import com.timcooki.jnuwiki.domain.docsRequest.dto.request.EditWriteReqDTO;
import com.timcooki.jnuwiki.domain.docsRequest.dto.request.NewWriteReqDTO;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequest;
import com.timcooki.jnuwiki.domain.docsRequest.mapper.DocsRequestMapper;
import com.timcooki.jnuwiki.domain.docsRequest.repository.DocsRequestRepository;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.domain.member.repository.MemberRepository;
import com.timcooki.jnuwiki.util.errors.exception.Exception400;
import com.timcooki.jnuwiki.util.errors.exception.Exception404;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocsRequestWriteService {
    private final DocsRequestRepository docsRequestRepository;
    private final MemberRepository memberRepository;
    private final DocsRepository docsRepository;

    public void createModifiedRequest(UserDetails userDetails, EditWriteReqDTO modifiedRequestWriteDto) {
        DocsRequestMapper mapper = Mappers.getMapper(DocsRequestMapper.class);
        Docs docs = docsRepository.findById(modifiedRequestWriteDto.docsId()).orElseThrow(() -> new Exception404("존재하지 않는 문서 입니다."));

        Member member = memberRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new Exception400("잘못된 요청입니다."));
        DocsRequest docsRequest = mapper.editDTOToEntity(modifiedRequestWriteDto, docs, member, DocsCategory.nameOf(modifiedRequestWriteDto.docsRequestCategory()));
        docsRequestRepository.save(docsRequest);
    }

    public void createNewDocsRequest(UserDetails userDetails, NewWriteReqDTO newWriteReqDTO) {
        DocsRequestMapper mapper = Mappers.getMapper(DocsRequestMapper.class);
        Member member = memberRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new Exception400("잘못된 요청입니다."));

        DocsRequest docsRequest = mapper.newDTOToEntity(newWriteReqDTO, member, DocsCategory.nameOf(newWriteReqDTO.docsRequestCategory()));
        docsRequestRepository.save(docsRequest);
    }
}
