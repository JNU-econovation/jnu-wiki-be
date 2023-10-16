package com.timcooki.jnuwiki.domain.member.service;

import com.timcooki.jnuwiki.domain.docs.repository.DocsRepository;
import com.timcooki.jnuwiki.domain.member.DTO.request.EditNicknameReqDTO;
import com.timcooki.jnuwiki.domain.member.DTO.request.EditPasswordReqDTO;
import com.timcooki.jnuwiki.domain.member.DTO.request.JoinReqDTO;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class MemberWriteServiceTest {
    @InjectMocks private MemberWriteService memberWriteService;
    @Mock private MemberRepository memberRepository;
    @Mock private DocsRepository docsRepository;
    @Mock private PasswordEncoder passwordEncoder;

    @Mock private Validator validator;

    @Test
    @DisplayName("회원가입")
    public void join_test() {
        // given
        JoinReqDTO joinReqDTO = JoinReqDTO.builder()
                .nickName("mmm")
                .password("123!aaa!")
                .email("mm@naver.com")
                .build();

        String newNickname = "moly";

        // stub
        Mockito.when(validator.isValidEmail(joinReqDTO.email())).thenReturn(true);
        Mockito.when(validator.isValidPassword(joinReqDTO.password())).thenReturn(true);

        // when
        memberWriteService.join(joinReqDTO);

        // then
    }

    @Test
    @DisplayName("닉네임 수정")
    public void editMemberNickname_test() {
        // given
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContextHolder.setContext(securityContext);

        Member member = Member.builder()
                .nickName("mmm")
                .role(MemberRole.USER)
                .password("123!aaa!")
                .email("mm@naver.com")
                .build();

        UserDetails userDetails = new MemberDetails(member);

        String newNickname = "moly";

        // stub
        Mockito.when(memberRepository.findByEmail(userDetails.getUsername())).thenReturn(Optional.of(member));
        Mockito.when(memberRepository.existsByNickName(any())).thenReturn(false);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);

        // when
        memberWriteService.editMemberNickname(new EditNicknameReqDTO(newNickname));

        // then
        assertThat(member.getNickName()).isEqualTo(newNickname);
    }

    @Test
    @DisplayName("비밀번호 수정")
    public void editMemberPassword_test() {
        // given
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContextHolder.setContext(securityContext);

        Member member = Member.builder()
                .nickName("mmm")
                .role(MemberRole.USER)
                .password("123!aaa!")
                .email("mm@naver.com")
                .build();

        UserDetails userDetails = new MemberDetails(member);

        String password = "dmklfa123?!!";

        // stub
        Mockito.when(memberRepository.findByEmail(userDetails.getUsername())).thenReturn(Optional.of(member));
        Mockito.when(validator.isValidPassword(password)).thenReturn(true);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        Mockito.when(passwordEncoder.encode(password)).thenReturn(password);

        // when
        memberWriteService.editMemberPassword(new EditPasswordReqDTO(password));

        // then
        assertThat(member.getPassword()).isEqualTo(password);
    }
}
