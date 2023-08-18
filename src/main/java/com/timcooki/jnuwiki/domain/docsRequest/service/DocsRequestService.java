package com.timcooki.jnuwiki.domain.docsRequest.service;

import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docs.repository.DocsRepository;
import com.timcooki.jnuwiki.domain.docsRequest.dto.request.EditWriteReqDTO;
import com.timcooki.jnuwiki.domain.docsRequest.dto.request.NewWriteReqDTO;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
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
import com.timcooki.jnuwiki.util.errors.exception.Exception400;
import com.timcooki.jnuwiki.util.errors.exception.Exception404;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DocsRequestService {

    private final DocsRequestRepository docsRequestRepository;

    private final MemberRepository memberRepository;
    private final DocsRepository docsRepository;

    public void createModifiedRequest(UserDetails userDetails, EditWriteReqDTO modifiedRequestWriteDto) {
        DocsRequestMapper mapper = Mappers.getMapper(DocsRequestMapper.class);

        // TODO - 15일 지났는지 체크 추가

        Docs docs = docsRepository.findById(modifiedRequestWriteDto.docsId()).orElseThrow(() -> new Exception404("존재하지 않는 문서 입니다."));

        Member member = memberRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new Exception400("잘못된 요청입니다."));
        DocsRequest docsRequest = mapper.editDTOToEntity(modifiedRequestWriteDto, docs, member, DocsCategory.nameOf(modifiedRequestWriteDto.docsRequestCategory()));
        docsRequestRepository.save(docsRequest);
    }

    public void createNewDocsRequest(UserDetails userDetails, NewWriteReqDTO newWriteReqDTO) {
        //checkMemberDuration(userDetails);

        DocsRequestMapper mapper = Mappers.getMapper(DocsRequestMapper.class);

        Member member = memberRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new Exception400("잘못된 요청입니다."));

        // TODO - 15일 지났는지 체크 추가

        DocsRequest docsRequest = mapper.newDTOToEntity(newWriteReqDTO, member, DocsCategory.nameOf(newWriteReqDTO.docsRequestCategory()));
        docsRequestRepository.save(docsRequest);
    }

    public void checkMemberDuration(UserDetails userDetails) {
        // 권한 확인(회원가입 15일)
        Member member = memberRepository.findByEmail(userDetails.getUsername()).get();
        if (ChronoUnit.DAYS.between(member.getCreatedAt(), LocalDateTime.now())>15){
            throw new RuntimeException("회원가입 후 15일이 지난 회원만 요청이 가능합니다.");//403 forbidden
        }
    }

    // 기본정보 수정 요청 목록 조회
    // TODO: 테스트 코드 작성
    public EditListReadResDTO getModifiedRequestList(Pageable pageable) {
        DocsRequestMapper mapper = Mappers.getMapper(DocsRequestMapper.class);

        List<DocsRequest> docsRequests = docsRequestRepository.findAllByDocsRequestType(DocsRequestType.MODIFIED, pageable).getContent();

        List<EditReadResDTO> modifiedList = docsRequests.stream()
                .map((docsRequest) -> mapper.editEntityToDTO(docsRequest, docsRequest.getDocsRequestCategory().getCategory()))
                .collect(Collectors.toList());

        if(modifiedList == null || modifiedList.isEmpty()){
            throw new Exception404("정보 수정 요청이 존재하지 않습니다.");
        }

        return EditListReadResDTO.builder()
                .modifiedRequestList(modifiedList)
                .build();
    }

    public NewListReadResDTO getCreatedRequestList(Pageable pageable) {
        DocsRequestMapper mapper = Mappers.getMapper(DocsRequestMapper.class);

        List<DocsRequest> docsRequests = docsRequestRepository.findAllByDocsRequestType(DocsRequestType.CREATED, pageable).getContent();

        List<NewReadResDTO> newList = docsRequests.stream()
                .map((docsRequest) -> mapper.newEntityToDTO(docsRequest, docsRequest.getDocsRequestCategory().getCategory()))
                .collect(Collectors.toList());

        if(newList == null || newList.isEmpty()){
            throw new Exception404("새 문서 요청이 존재하지 않습니다.");
        }

        return NewListReadResDTO.builder()
                .createdRequestList(newList)
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


    public void rejectRequest(Long docsRequestId) {
        // 요청 존재 확인
        DocsRequest docsRequest = docsRequestRepository.findById(docsRequestId).orElseThrow(() -> new Exception404("존재하지 않는 요청입니다."));

        docsRequestRepository.delete(docsRequest);
    }

    public boolean hasRequest(Long docsRequestId) {
        return docsRequestRepository.findById(docsRequestId).isPresent();
    }
}
