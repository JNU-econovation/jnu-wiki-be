package com.timcooki.jnuwiki.domain.docsRequest.service;

import com.timcooki.jnuwiki.domain.docs.DTO.DocsCreateDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.DocsUpdateInfoDTO;
import com.timcooki.jnuwiki.domain.docsRequest.dto.request.CreatedRequestWriteDTO;
import com.timcooki.jnuwiki.domain.docsRequest.dto.request.ModifiedRequestWriteDTO;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequest;
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

public class DocsRequestService {

    private DocsRequestRepository docsRequestRepository;

    public DocsRequest createModifiedRequest(ModifiedRequestWriteDTO modifiedRequestWriteDto) {
        return null;
    }

    public void createNewDocsRequest(CreatedRequestWriteDTO createdRequestDto) {
        DocsRequestMapper mapper = Mappers.getMapper(DocsRequestMapper.class);

        DocsRequest docsRequest = mapper.toEntity(createdRequestDto);
        docsRequestRepository.save(docsRequest);
    }

    public void rejectRequest(Long docsRequestId) {
        // 요청 존재 확인
        Optional<DocsRequest> docsRequest = docsRequestRepository.findById(docsRequestId);
        if(docsRequest.isEmpty()){
            throw new RuntimeException("존재하지 않는 요청입니다.");
        }else {
            docsRequestRepository.deleteById(docsRequestId);
        }
    }


    /*
    public Page<ModifiedFindAllReqDTO> getModifiedRequestList(Pageable pageable) {
    }

    public Page<CreatedFindAllReqDTO> getCreatedRequestList(Pageable pageable) {
    }

    public ModifiedFindByIdReqDTO getOneModifiedRequest(String docsRequestId) {
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

     */
}
