package com.timcooki.jnuwiki.domain.docsRequest.service;

import com.timcooki.jnuwiki.domain.docsRequest.dto.request.EditWriteReqDTO;
import com.timcooki.jnuwiki.domain.docsRequest.dto.request.NewWriteReqDTO;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequest;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequestType;
import com.timcooki.jnuwiki.domain.docsRequest.mapper.DocsRequestMapper;
import com.timcooki.jnuwiki.domain.docsRequest.repository.DocsRequestRepository;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.EditListReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.EditReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.NewListReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.NewReadResDTO;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocsRequestService {

    private final DocsRequestRepository docsRequestRepository;

    private final MemberRepository memberRepository;

    public void createModifiedRequest(EditWriteReqDTO modifiedRequestWriteDto) {
        DocsRequestMapper mapper = Mappers.getMapper(DocsRequestMapper.class);

        DocsRequest docsRequest = mapper.editDTOToEntity(modifiedRequestWriteDto);
        docsRequestRepository.save(docsRequest);
    }

    public void createNewDocsRequest(UserDetails userDetails, NewWriteReqDTO newWriteReqDTO) {

        checkMemberDuration(userDetails);


        DocsRequestMapper mapper = Mappers.getMapper(DocsRequestMapper.class);

        DocsRequest docsRequest = mapper.newDTOToEntity(newWriteReqDTO);
        docsRequestRepository.save(docsRequest);
    }

    public void checkMemberDuration(UserDetails userDetails) {
        // 권한 확인(회원가입 15일)
        Member member = memberRepository.findByEmail(userDetails.getUsername()).get();
        if (ChronoUnit.DAYS.between(member.getCreatedAt(), LocalDate.now())>15){
            throw new RuntimeException("회원가입 후 15일이 지난 회원만 요청이 가능합니다.");//403 forbidden
        }
    }

    // 기본정보 수정 요청 목록 조회
    // TODO: 테스트 코드 작성
    public EditListReadResDTO getModifiedRequestList(Pageable pageable) {
        DocsRequestMapper mapper = Mappers.getMapper(DocsRequestMapper.class);

        List<DocsRequest> docsRequests = docsRequestRepository.findAllByDocsRequestType(DocsRequestType.MODIFIED, pageable);

        List<EditReadResDTO> modifiedList = docsRequests.stream()
                .map(mapper::editEntityToDTO)
                .collect(Collectors.toList());

        return EditListReadResDTO.builder()
                .modifiedRequestList(modifiedList)
                .build();
    }

    public NewListReadResDTO getCreatedRequestList(Pageable pageable) {
        DocsRequestMapper mapper = Mappers.getMapper(DocsRequestMapper.class);

        List<DocsRequest> docsRequests = docsRequestRepository.findAllByDocsRequestType(DocsRequestType.CREATED, pageable);

        List<NewReadResDTO> newList = docsRequests.stream()
                .map(mapper::newEntityToDTO)
                .collect(Collectors.toList());

        return NewListReadResDTO.builder()
                .createdRequestList(newList)
                .build();
    }

    public EditReadResDTO getOneModifiedRequest(Long docsRequestId) {
        DocsRequestMapper mapper = Mappers.getMapper(DocsRequestMapper.class);

        DocsRequest docsRequest = docsRequestRepository.findById(docsRequestId).get();

        EditReadResDTO editReadResDTO = mapper.editEntityToDTO(docsRequest);

        return editReadResDTO;
    }

    public NewReadResDTO getOneCreatedRequest(Long docsRequestId) {
        DocsRequestMapper mapper = Mappers.getMapper(DocsRequestMapper.class);

        DocsRequest docsRequest = docsRequestRepository.findById(docsRequestId).get();

        NewReadResDTO newReadResDTO = mapper.newEntityToDTO(docsRequest);

        return newReadResDTO;
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

    public boolean hasRequest(Long docsRequestId) {
        return docsRequestRepository.findById(docsRequestId).isPresent();
    }
}
