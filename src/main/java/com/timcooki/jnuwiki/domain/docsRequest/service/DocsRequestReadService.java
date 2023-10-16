package com.timcooki.jnuwiki.domain.docsRequest.service;

import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequest;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequestType;
import com.timcooki.jnuwiki.domain.docsRequest.mapper.DocsRequestMapper;
import com.timcooki.jnuwiki.domain.docsRequest.repository.DocsRequestRepository;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.EditListReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.EditReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.NewListReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.NewReadResDTO;
import com.timcooki.jnuwiki.util.errors.exception.Exception400;
import com.timcooki.jnuwiki.util.errors.exception.Exception404;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DocsRequestReadService {

    private final DocsRequestRepository docsRequestRepository;


    // 기본정보 수정 요청 목록 조회
    public EditListReadResDTO getModifiedRequestList(Pageable pageable) {
        DocsRequestMapper mapper = Mappers.getMapper(DocsRequestMapper.class);
        Page<DocsRequest> docsRequests = docsRequestRepository.findAllByDocsRequestType(DocsRequestType.MODIFIED, pageable);

        List<EditReadResDTO> modifiedList = docsRequests.getContent().stream()
                .map((docsRequest) -> mapper.editEntityToDTO(docsRequest, docsRequest.getDocsRequestCategory().getCategory()))
                .collect(Collectors.toList());

        if(modifiedList == null || modifiedList.isEmpty()){
            throw new Exception404("정보 수정 요청이 존재하지 않습니다.");
        }

        return EditListReadResDTO.builder()
                .modifiedRequestList(modifiedList)
                .totalPages(docsRequests.getTotalPages())
                .build();
    }

    public NewListReadResDTO getCreatedRequestList(Pageable pageable) {
        DocsRequestMapper mapper = Mappers.getMapper(DocsRequestMapper.class);
        Page<DocsRequest> docsRequests = docsRequestRepository.findAllByDocsRequestType(DocsRequestType.CREATED, pageable);

        List<NewReadResDTO> newList = docsRequests.stream()
                .map((docsRequest) -> mapper.newEntityToDTO(docsRequest, docsRequest.getDocsRequestCategory().getCategory()))
                .collect(Collectors.toList());

        if(newList == null || newList.isEmpty()){
            throw new Exception404("새 문서 요청이 존재하지 않습니다.");
        }

        return NewListReadResDTO.builder()
                .createdRequestList(newList)
                .totalPages(docsRequests.getTotalPages())
                .build();
    }

    public EditReadResDTO getOneModifiedRequest(Long docsRequestId) {
        DocsRequestMapper mapper = Mappers.getMapper(DocsRequestMapper.class);
        DocsRequest docsRequest = docsRequestRepository.findById(docsRequestId).orElseThrow(() -> new Exception404("존재하지 않는 요청입니다."));

        // 수정된문서 요청이 아닌경우
        if(docsRequest.getDocsRequestType().equals(DocsRequestType.CREATED)){
            throw new Exception400("잘못된 요청입니다.");
        }

        EditReadResDTO editReadResDTO = mapper.editEntityToDTO(docsRequest, docsRequest.getDocsRequestCategory().getCategory());
        return editReadResDTO;
    }

    public NewReadResDTO getOneCreatedRequest(Long docsRequestId) {
        DocsRequestMapper mapper = Mappers.getMapper(DocsRequestMapper.class);
        DocsRequest docsRequest = docsRequestRepository.findById(docsRequestId).orElseThrow(() -> new Exception404("존재하지 않는 요청입니다."));

        // 새문서 요청이 아닌경우
        if(docsRequest.getDocsRequestType().equals(DocsRequestType.MODIFIED)){
            throw new Exception400("잘못된 요청입니다.");
        }

        NewReadResDTO newReadResDTO = mapper.newEntityToDTO(docsRequest, docsRequest.getDocsRequestCategory().getCategory());
        return newReadResDTO;
    }
}
