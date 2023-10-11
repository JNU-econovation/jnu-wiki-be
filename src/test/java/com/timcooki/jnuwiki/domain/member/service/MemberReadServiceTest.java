package com.timcooki.jnuwiki.domain.member.service;

import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docs.repository.DocsRepository;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import com.timcooki.jnuwiki.domain.member.DTO.response.ReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.ScrapListResDTO;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.domain.member.entity.MemberRole;
import com.timcooki.jnuwiki.domain.member.repository.MemberRepository;
import com.timcooki.jnuwiki.domain.security.config.MemberDetails;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class MemberReadServiceTest {
    @InjectMocks private MemberReadService memberReadService;
    @Mock private MemberRepository memberRepository;
    @Mock private DocsRepository docsRepository;

    @Test
    @DisplayName("내 정보 조회")
    public void getInfo_test() {
        // given
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContextHolder.setContext(securityContext);

        Member member = Member.builder()
                .nickName("mmm")
                .role(MemberRole.USER)
                .password("123!aaa")
                .email("mm@naver.com")
                .build();
        UserDetails userDetails = new MemberDetails(member);

        // stub
        Mockito.when(memberRepository.findByEmail(member.getEmail())).thenReturn(Optional.of(member));
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);

        // when
        ReadResDTO memberInfo = memberReadService.getInfo();

        // then
        assertThat(memberInfo.nickName()).isEqualTo(member.getNickName());
    }

    @Test
    @DisplayName("스크랩한 글 조회")
    public void getScrappedDocs_test() {
        // given
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContextHolder.setContext(securityContext);

        Member member = Member.builder()
                .nickName("mmm")
                .role(MemberRole.USER)
                .password("123!aaa")
                .email("mm@naver.com")
                .build();

        UserDetails userDetails = new MemberDetails(member);

        Docs docs1 = Docs.builder()
                .docsName("1번 문서")
                .docsLocation(DocsLocation.builder()
                        .lat(231.342)
                        .lng(4234.5)
                        .build())
                .docsCategory(DocsCategory.CAFE)
                .build();

        Docs docs2 = Docs.builder()
                .docsName("2")
                .docsLocation(DocsLocation.builder()
                        .lat(231.342)
                        .lng(4234.5)
                        .build())
                .docsCategory(DocsCategory.CAFE)
                .build();

        Page<Docs> page = new PageImpl<>(List.of(docs1, docs2));

        // stub
        Mockito.when(memberRepository.findByEmail(member.getEmail())).thenReturn(Optional.of(member));
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        Mockito.when(docsRepository.mFindScrappedDocsByMemberId(any(), any()))
                .thenReturn(page);

        // when
        ScrapListResDTO list = memberReadService.getScrappedDocs(PageRequest.of(0, 2));

        // then
        assertThat(list.scrapList().get(0).docsName()).isEqualTo(docs1.getDocsName());
        assertThat(list.totalPages()).isEqualTo(page.getTotalPages());
    }
}
