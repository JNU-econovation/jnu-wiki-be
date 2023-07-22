package com.timcooki.jnuwiki.domain.docsRequest.service;

import com.timcooki.jnuwiki.domain.docsRequest.dto.request.CreatedRequestWriteDTO;
import com.timcooki.jnuwiki.domain.docsRequest.dto.request.ModifiedRequestWriteDTO;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequest;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.CreatedFindAllReqDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.CreatedFindByIdReqDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.ModifiedFindByIdReqDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.ModifiedFindAllReqDTO;

import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class DocsRequestService {

    private final DocsRequestRepository docsRequestRepository;
    private final MemberRepository memberRepository;

    public void createNewDocsRequest(UserDetails userDetails, CreatedRequestWriteDTO createdRequestDto) {

        // 권한 확인(회원가입 15일)
        Member member = memberRepository.findByEmail(userDetails.getUsername()).get();
        if (ChronoUnit.DAYS.between(member.getCreatedAt(), LocalDate.now())>15){
            throw new RuntimeException("회원가입 후 15일이 지난 회원만 요청이 가능합니다.");//403 forbidden
        }


        DocsRequestMapper mapper = Mappers.getMapper(DocsRequestMapper.class);

        DocsRequest docsRequest = mapper.toEntity(createdRequestDto);
        docsRequestRepository.save(docsRequest);
    }
    public DocsRequest createModifiedRequest(ModifiedRequestWriteDTO modifiedRequestWriteDto) {
        return null;
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
