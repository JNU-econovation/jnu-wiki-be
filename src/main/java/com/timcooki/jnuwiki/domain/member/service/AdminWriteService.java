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
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AdminWriteService {
    private final DocsRequestRepository docsRequestRepository;
    private final DocsRepository docsRepository;
    private final DocsArchiveRepository docsArchiveRepository;

    @Transactional
    public NewApproveResDTO approveNewDocs(Long docsRequestId) {
        DocsRequest docsRequest = docsRequestRepository.findById(docsRequestId).orElseThrow(
                () -> new Exception404("존재하지 않는 요청입니다.")
        );

        Docs docs = Docs.of(docsRequest);
        docsRepository.save(docs);
        docsRequestRepository.deleteById(docsRequestId);

        return NewApproveResDTO.of(docs);
    }

    @Transactional
    public InfoEditResDTO updateDocsFromRequest(Long docsRequestId) {
        DocsRequest modifiedRequest = docsRequestRepository.findById(docsRequestId).orElseThrow(
                () -> new Exception404("존재하지 않는 요청입니다.")
        );

        Long docsId = modifiedRequest.getDocs().getDocsId();
        Docs docs = docsRepository.findById(docsId).orElseThrow(
                () -> new Exception404("수정하려는 문서가 존재하지 않습니다.")
        );

        docsArchiveRepository.save(docs);

        docs.updateBasicInfo(modifiedRequest);
        docsRequestRepository.deleteById(docsRequestId);
        return InfoEditResDTO.of(docs);
    }

    public void rejectRequest(Long docsRequestId) {
        DocsRequest docsRequest = docsRequestRepository.findById(docsRequestId)
                .orElseThrow(() -> new Exception404("존재하지 않는 요청입니다."));

        docsRequestRepository.delete(docsRequest);
    }
}
