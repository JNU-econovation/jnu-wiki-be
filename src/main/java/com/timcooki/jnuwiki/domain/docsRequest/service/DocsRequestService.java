package com.timcooki.jnuwiki.domain.docsRequest.service;

import com.timcooki.jnuwiki.domain.docs.DTO.NewApproveResDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.DocsUpdateInfoDTO;
import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docsArchive.repository.DocsArchiveRepository;
import com.timcooki.jnuwiki.domain.docsRequest.dto.request.NewWriteReqDTO;
import com.timcooki.jnuwiki.domain.docsRequest.dto.request.EditWriteReqDTO;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequest;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequestType;
import com.timcooki.jnuwiki.domain.docsRequest.mapper.DocsRequestMapper;
import com.timcooki.jnuwiki.domain.docsRequest.repository.DocsRequestRepository;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.NewListReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.NewReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.EditReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.EditListReadResDTO;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocsRequestService {

    private final DocsRequestRepository docsRequestRepository;

    // TODO: 머지 후 docsRepository의 메서드 오류뜨는지 확인 - 쿠키가 레포 구현했길래 안함
    private final DocsRepository docsRepository;

    private final DocsArchiveRepository docsArchiveRepository;

    public void createModifiedRequest(EditWriteReqDTO modifiedRequestWriteDto) {
        DocsRequestMapper mapper = Mappers.getMapper(DocsRequestMapper.class);

        DocsRequest docsRequest = mapper.modifiedDTOToEntity(modifiedRequestWriteDto);
        docsRequestRepository.save(docsRequest);
    }

    public void createNewDocsRequest(NewWriteReqDTO createdRequestDto) {
        DocsRequestMapper mapper = Mappers.getMapper(DocsRequestMapper.class);

        DocsRequest docsRequest = mapper.toEntity(createdRequestDto);
        docsRequestRepository.save(docsRequest);
    }

    // 기본정보 수정 요청 목록 조회
    // TODO: 테스트 코드 작성
    public EditListReadResDTO getModifiedRequestList(Pageable pageable) {
        DocsRequestMapper mapper = Mappers.getMapper(DocsRequestMapper.class);

        List<DocsRequest> docsRequests = docsRequestRepository.findAllByDocsRequestType(DocsRequestType.MODIFIED, pageable);

        List<EditReadResDTO> modifiedList = docsRequests.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());

        return EditListReadResDTO.builder()
                .modifiedRequestList(modifiedList)
                .build();
    }

    public Page<NewListReadResDTO> getCreatedRequestList(Pageable pageable) {
    }

    public EditReadResDTO getOneModifiedRequest(Long docsRequestId) {
        DocsRequestMapper mapper = Mappers.getMapper(DocsRequestMapper.class);

        DocsRequest docsRequest = docsRequestRepository.findById(docsRequestId).get();

        EditReadResDTO editReadResDTO = mapper.toDTO(docsRequest);

        return editReadResDTO;
    }

    public NewReadResDTO getOneCreatedRequest(String docsRequestId) {
    }

    public NewApproveResDTO createDocsFromRequest(String docsRequestId) {
    }

    public DocsUpdateInfoDTO updateDocsFromRequest(Long docsRequestId) {
        // 승락받은 요청 조회
        DocsRequest modifiedRequest = docsRequestRepository.findById(docsRequestId).get();

        // 수정할 문서 조회
        Long docsId = modifiedRequest.getDocs().getDocsId();
        Docs docs = docsRepository.findById(docsId).get();

        // 수정전 문서는 아카이브 레포에 저장
        docsArchiveRepository.save(docs);

        // 요청에 따라 업데이트
        docs.updateBasicInfo(
                modifiedRequest.getDocsName(),
                modifiedRequest.getDocsLocation(),
                modifiedRequest.getDocsCategory());

        docsRepository.deleteById(docsRequestId); // 처리된 요청 삭제

        return DocsUpdateInfoDTO.builder()
                .id(docs.getDocsId())
                .docsRequestName(docs.getDocsName())
                .docsRequestLocation(docs.getDocsLocation())
                .docsContent(docs.getDocsContent())
                .docsRequestCategory(docs.getDocsCategory())
                .docsCreatedAt(LocalDateTime.now())
                .build();
    }

    public void rejectRequest(String docsRequestId) {
    }

    public boolean hasRequest(String docsRequestId) {
    }
}
