package com.timcooki.jnuwiki.domain.docsRequest.service;

import com.timcooki.jnuwiki.domain.docs.DTO.DocsCreateDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.DocsUpdateInfoDTO;
import com.timcooki.jnuwiki.domain.docsRequest.dto.request.CreatedRequestWriteDTO;
import com.timcooki.jnuwiki.domain.docsRequest.dto.request.ModifiedRequestWriteDTO;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequest;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequestType;
import com.timcooki.jnuwiki.domain.docsRequest.mapper.DocsRequestMapper;
import com.timcooki.jnuwiki.domain.docsRequest.repository.DocsRequestRepository;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.CreatedFindAllReqDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.CreatedFindByIdReqDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.ModifiedFindByIdReqDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.ModifiedFindAllReqDTO;

import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.domain.member.entity.MemberRole;
import com.timcooki.jnuwiki.domain.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

public class DocsRequestService {

    private DocsRequestRepository docsRequestRepository;

    public void createModifiedRequest(ModifiedRequestWriteDTO modifiedRequestWriteDto) {
        DocsRequestMapper mapper = Mappers.getMapper(DocsRequestMapper.class);

        DocsRequest docsRequest = mapper.modifiedDTOToEntity(modifiedRequestWriteDto);
        docsRequestRepository.save(docsRequest);
    }

    public void createNewDocsRequest(CreatedRequestWriteDTO createdRequestDto) {
        DocsRequestMapper mapper = Mappers.getMapper(DocsRequestMapper.class);

        DocsRequest docsRequest = mapper.toEntity(createdRequestDto);
        docsRequestRepository.save(docsRequest);
    }

    // 기본정보 수정 요청 목록 조회
    // TODO: 테스트 코드 작성
    public ModifiedFindAllReqDTO getModifiedRequestList(Pageable pageable) {
        DocsRequestMapper mapper = Mappers.getMapper(DocsRequestMapper.class);

        List<DocsRequest> docsRequests = docsRequestRepository.findAllByDocsRequestType(DocsRequestType.MODIFIED, pageable);

        List<ModifiedFindByIdReqDTO> modifiedList = docsRequests.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());

        return ModifiedFindAllReqDTO.builder()
                .modifiedRequestList(modifiedList)
                .build();
    }

    public Page<CreatedFindAllReqDTO> getCreatedRequestList(Pageable pageable) {
    }

    public ModifiedFindByIdReqDTO getOneModifiedRequest(Long docsRequestId) {
        DocsRequestMapper mapper = Mappers.getMapper(DocsRequestMapper.class);

        DocsRequest docsRequest = docsRequestRepository.findById(docsRequestId).get();

        ModifiedFindByIdReqDTO modifiedFindByIdReqDTO = mapper.toDTO(docsRequest);

        return modifiedFindByIdReqDTO;
    }

    public CreatedFindByIdReqDTO getOneCreatedRequest(String docsRequestId) {
    }

    public DocsCreateDTO createDocsFromRequest(String docsRequestId) {
    }

    public DocsUpdateInfoDTO updateDocsFromRequest(String docsRequestId) {
    }

    public void rejectRequest(String docsRequestId) {
    }

    public boolean hasRequest(String docsRequestId) {
    }
}
