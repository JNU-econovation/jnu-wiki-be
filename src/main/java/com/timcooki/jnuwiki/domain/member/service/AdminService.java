package com.timcooki.jnuwiki.domain.member.service;

import com.timcooki.jnuwiki.domain.docs.DTO.response.InfoEditResDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.response.NewApproveResDTO;
import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docs.repository.DocsRepository;
import com.timcooki.jnuwiki.domain.docsArchive.repository.DocsArchiveRepository;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequest;
import com.timcooki.jnuwiki.domain.docsRequest.repository.DocsRequestRepository;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.domain.member.repository.MemberRepository;
import com.timcooki.jnuwiki.util.errors.exception.Exception401;
import com.timcooki.jnuwiki.util.errors.exception.Exception403;
import com.timcooki.jnuwiki.util.errors.exception.Exception404;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final MemberRepository memberRepository;
    private final DocsRequestRepository docsRequestRepository;
    private final DocsRepository docsRepository;
    private final DocsArchiveRepository docsArchiveRepository;

    // TODO - 예외처리 수정
    // 새 문서 요청 승락
    public NewApproveResDTO approveNewDocs(UserDetails userDetails, Long docsRequestId){
        // 권한 확인
        if(!findByEmail(userDetails.getUsername())){
            throw new Exception401("로그인이 필요한 기능입니다."); // 401
        }

        Member member  = memberRepository.findByEmail(userDetails.getUsername()).orElseThrow(); // 위에서 처리될듯?
        Optional<DocsRequest> docsRequest = docsRequestRepository.findById(docsRequestId);
        // 요청 존재 확인
        if(docsRequest.isEmpty()){
            throw new Exception404("존재하지 않는 요청입니다."); // 404
        }

        // 문서 등록
        Docs docs = Docs.builder()
                .createdBy(member)
                .docsCategory(docsRequest.get().getDocsRequestCategory())
                .docsLocation(docsRequest.get().getDocsRequestLocation())
                .docsName(docsRequest.get().getDocsRequestName())
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

    public InfoEditResDTO updateDocsFromRequest(UserDetails userDetails, Long docsRequestId) {
        // 권한 확인
        if(!findByEmail(userDetails.getUsername())){
            throw new Exception401("로그인이 필요한 기능입니다."); // 401
        }
        if(!userDetails.getAuthorities().contains("ADMIN")){
            throw new Exception403("관리자 권한이 없습니다."); // 403
        }

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

        docsRepository.deleteById(docsRequestId); // 처리된 요청 삭제

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
