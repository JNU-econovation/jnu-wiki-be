package com.timcooki.jnuwiki.domain.member.service;

import com.timcooki.jnuwiki.domain.docs.DTO.response.InfoEditResDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.response.NewApproveResDTO;
import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docs.repository.DocsRepository;
import com.timcooki.jnuwiki.domain.docsArchive.repository.DocsArchiveRepository;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequest;
import com.timcooki.jnuwiki.domain.docsRequest.repository.DocsRequestRepository;
import com.timcooki.jnuwiki.domain.member.repository.MemberRepository;
import com.timcooki.jnuwiki.util.errors.exception.Exception404;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final MemberRepository memberRepository;
    private final DocsRequestRepository docsRequestRepository;
    private final DocsRepository docsRepository;
    private final DocsArchiveRepository docsArchiveRepository;

    // TODO - 예외처리 수정
    // 새 문서 요청 승락
    public NewApproveResDTO approveNewDocs(Long docsRequestId){


        DocsRequest docsRequest = docsRequestRepository.findById(docsRequestId).orElseThrow(
                () -> new Exception404("존재하지 않는 요청입니다.")
        );

        // 문서 등록
        Docs docs = Docs.builder()
                .createdBy(docsRequest.getDocsRequestedBy())
                .docsCategory(docsRequest.getDocsRequestCategory())
                .docsLocation(docsRequest.getDocsRequestLocation())
                .docsName(docsRequest.getDocsRequestName())
                .build();
        docsRepository.save(docs);

        // DocsRequest 삭제
        docsRequestRepository.deleteById(docsRequestId);

        return NewApproveResDTO.builder()
                .id(docs.getDocsId())
                .docsCategory(docs.getDocsCategory())
                .docsName(docs.getDocsName())
                .docsLocation(docs.getDocsLocation())
                .build();

        //DocsCreateDTO createdDocs = docsRequestService.createDocsFromRequest(docsRequestId);
    }

    @Transactional
    public InfoEditResDTO updateDocsFromRequest(Long docsRequestId) {

        // 승락받은 요청 조회
        DocsRequest modifiedRequest = docsRequestRepository.findById(docsRequestId).orElseThrow(
                () -> new Exception404("존재하지 않는 요청입니다.")
        );

        // 수정할 문서 조회
        Long docsId = modifiedRequest.getDocs().getDocsId();
        Docs docs = docsRepository.findById(docsId).orElseThrow(
                () -> new Exception404("수정하려는 문서가 존재하지 않습니다.")
        );

        // 수정전 문서는 아카이브 레포에 저장
        docsArchiveRepository.save(docs);

        // 요청에 따라 업데이트
        docs.updateBasicInfo(
                modifiedRequest.getDocsRequestName(),
                modifiedRequest.getDocsRequestLocation(),
                modifiedRequest.getDocsRequestCategory());

        docsRequestRepository.deleteById(docsRequestId);

        return InfoEditResDTO.builder()
                .docsId(docs.getDocsId())
                .docsName(docs.getDocsName())
                .docsLocation(docs.getDocsLocation())
                .docsContent(docs.getDocsContent())
                .docsCategory(docs.getDocsCategory())
                .docsModifiedAt(LocalDateTime.now())
                .build();
    }


    public boolean findByEmail(String email){

        if(memberRepository.findByEmail(email).isPresent()){
            return true;
        }else {
            return false;
        }
    }
}
