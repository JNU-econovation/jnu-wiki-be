package com.timcooki.jnuwiki.domain.member.service;

import com.timcooki.jnuwiki.domain.docs.repository.DocsRepository;
import com.timcooki.jnuwiki.domain.member.DTO.response.ReadResDTO;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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
}
