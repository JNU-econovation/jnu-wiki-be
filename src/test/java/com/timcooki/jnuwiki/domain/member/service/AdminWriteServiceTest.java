package com.timcooki.jnuwiki.domain.member.service;

import com.timcooki.jnuwiki.domain.docs.DTO.response.NewApproveResDTO;
import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docs.repository.DocsRepository;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequest;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequestType;
import com.timcooki.jnuwiki.domain.docsRequest.repository.DocsRequestRepository;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.domain.member.entity.MemberRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class AdminWriteServiceTest {
    @InjectMocks
    private AdminWriteService adminWriteService;
    @Mock private DocsRequestRepository docsRequestRepository;
    @Mock private DocsRepository docsRepository;
    @Mock private PasswordEncoder passwordEncoder;

    @Mock private Validator validator;

    @Test
    @DisplayName("새 문서 요청 승락")
    public void approveNewDocs_test() {
        // given
        Long docsRequestId = 1L;

        Member member = Member.builder()
                .nickName("mmm")
                .role(MemberRole.USER)
                .password("123!aaa")
                .email("mm@naver.com")
                .build();

        DocsRequest docsRequest = DocsRequest.builder()
                .docs(null)
                .docsRequestType(DocsRequestType.CREATED)
                .docsRequestedBy(member)
                .docsRequestLocation(DocsLocation.builder()
                        .lat(324.342)
                        .lng(432.34)
                        .build())
                .docsRequestCategory(DocsCategory.CAFE)
                .docsRequestName("바꾸기")
                .build();

        // stub
        Mockito.when(docsRequestRepository.findById(docsRequestId)).thenReturn(Optional.of(docsRequest));

        // when
        NewApproveResDTO newApproveResDTO = adminWriteService.approveNewDocs(docsRequestId);

        // then
        assertThat(docsRequest.getDocsRequestName()).isEqualTo(newApproveResDTO.docsName());
    }

    @Test
    @DisplayName("수정 문서 요청 승락")
    public void updateDocsFromRequest_test() {
        // given
        Long docsRequestId = 1L;

        Member member = Member.builder()
                .nickName("mmm")
                .role(MemberRole.USER)
                .password("123!aaa")
                .email("mm@naver.com")
                .build();

        Docs docs = Docs.builder()
                .docsCategory(DocsCategory.CAFE)
                .docsLocation(DocsLocation.builder()
                        .lat(324.342)
                        .lng(432.34)
                        .build())
                .docsName("fsdf")
                .createdBy(member)
                .build();

        DocsRequest docsRequest = DocsRequest.builder()
                .docs(docs)
                .docsRequestType(DocsRequestType.MODIFIED)
                .docsRequestedBy(member)
                .docsRequestLocation(DocsLocation.builder()
                        .lat(324.342)
                        .lng(432.34)
                        .build())
                .docsRequestCategory(DocsCategory.CAFE)
                .docsRequestName("바꾸기")
                .build();

        // stub
        Mockito.when(docsRequestRepository.findById(docsRequestId)).thenReturn(Optional.of(docsRequest));

        // when
        NewApproveResDTO newApproveResDTO = adminWriteService.approveNewDocs(docsRequestId);

        // then
        assertThat(docsRequest.getDocsRequestName()).isEqualTo(newApproveResDTO.docsName());
    }

}
