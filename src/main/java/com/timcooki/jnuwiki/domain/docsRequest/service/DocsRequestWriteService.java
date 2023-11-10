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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DocsRequestWriteService {
    private final DocsRequestRepository docsRequestRepository;
    private final MemberRepository memberRepository;
    private final DocsRepository docsRepository;

    private String getEmail(){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String email = loggedInUser.getName();
        return email;
    }

    @Transactional
    public void createModifiedRequest(EditWriteReqDTO modifiedRequestWriteDto) {
        String email = getEmail();
        DocsRequestMapper mapper = Mappers.getMapper(DocsRequestMapper.class);
        Docs docs = docsRepository.findById(modifiedRequestWriteDto.docsId()).orElseThrow(() -> new Exception404("존재하지 않는 문서 입니다."));

        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new Exception400("잘못된 요청입니다."));
        DocsRequest docsRequest = mapper.editDTOToEntity(modifiedRequestWriteDto, docs, member, DocsCategory.nameOf(modifiedRequestWriteDto.docsRequestCategory()));
        // docsRequestRepository.save(docsRequest);

        docs.updateBasicInfo(docsRequest.getDocsRequestName(),
                        docsRequest.getDocsRequestLocation(),
                        docsRequest.getDocsRequestCategory());

    }

    @Transactional
    public void createNewDocsRequest(NewWriteReqDTO newWriteReqDTO) {
        String email = getEmail();
        DocsRequestMapper mapper = Mappers.getMapper(DocsRequestMapper.class);
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new Exception400("잘못된 요청입니다."));

        DocsRequest docsRequest = mapper.newDTOToEntity(newWriteReqDTO, member, DocsCategory.nameOf(newWriteReqDTO.docsRequestCategory()));
        // docsRequestRepository.save(docsRequest);

        Docs docs = Docs.builder()
                .createdBy(docsRequest.getDocsRequestedBy())
                .docsCategory(docsRequest.getDocsRequestCategory())
                .docsLocation(docsRequest.getDocsRequestLocation())
                .docsName(docsRequest.getDocsRequestName())
                .build();
        docsRepository.save(docs);
    }
}
