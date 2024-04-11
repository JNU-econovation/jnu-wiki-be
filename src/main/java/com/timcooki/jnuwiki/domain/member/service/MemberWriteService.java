package com.timcooki.jnuwiki.domain.member.service;

import com.timcooki.jnuwiki.domain.member.DTO.request.*;
import com.timcooki.jnuwiki.domain.member.DTO.response.LoginResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.WrapLoginResDTO;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.domain.member.entity.MemberRole;
import com.timcooki.jnuwiki.domain.member.repository.MemberRepository;
import com.timcooki.jnuwiki.domain.security.entity.RefreshToken;
import com.timcooki.jnuwiki.domain.security.service.RefreshTokenService;
import com.timcooki.jnuwiki.util.CookieUtil;
import com.timcooki.jnuwiki.domain.security.config.JwtProvider;
import com.timcooki.jnuwiki.util.TimeFormatter;
import com.timcooki.jnuwiki.util.errors.exception.Exception400;
import com.timcooki.jnuwiki.util.errors.exception.Exception404;
import java.time.Instant;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class MemberWriteService {
    private final MemberValidator memberValidator;
    private final MemberRepository memberRepository;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;

    public WrapLoginResDTO login(HttpServletResponse response, LoginReqDTO loginReqDTO) {
        String email = loginReqDTO.email();
        String password = loginReqDTO.password();

        Member member = getMember(email);
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new Exception400("이메일 또는 비밀번호를 확인해주세요. ");
        }

        Long memberId = member.getMemberId();
        String memberRole = member.getRole().toString();
        String accessToken = JwtProvider.createAccessToken(email, memberRole);

        // header 생성
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(member);
        Instant accessTokenExpiration = JwtProvider.getATExpiration(accessToken);
        Instant refreshTokenExpiration = JwtProvider.getExpiration(refreshToken.getToken());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, accessToken);

        CookieUtil.addCookie(response, "refresh-token", refreshToken.getToken(), (int) refreshTokenExpiration.toEpochMilli());

        return WrapLoginResDTO.builder()
                .headers(httpHeaders)
                .body(LoginResDTO.builder()
                        .id(memberId)
                        .role(memberRole)
                        .accessTokenExpiration(accessTokenExpiration.toEpochMilli())
                        .accessTokenFormattedExpiration(TimeFormatter.format(accessTokenExpiration))
                        .refreshTokenExpiration(refreshTokenExpiration.toEpochMilli())
                        .refreshTokenFormattedExpiration(TimeFormatter.format(refreshTokenExpiration))
                        .build())
                .build();
    }


    public void join(JoinReqDTO joinReqDTO) {
        memberValidator.validateEmailDuplication(joinReqDTO.email());

        memberRepository.save(
                Member.builder()
                        .email(joinReqDTO.email())
                        .nickName(joinReqDTO.nickName())
                        .role(MemberRole.USER)
                        .password(passwordEncoder.encode(joinReqDTO.password()))
                        .build()
        );
    }

    @Transactional
    public void editMemberNickname(EditNicknameReqDTO newNickname) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails memberDetails = (UserDetails) principal;

        Member member = getMember(memberDetails.getUsername());

        if (memberRepository.existsByNickName(memberDetails.getUsername())) {
            throw new Exception400("중복된 닉네임 입니다.:" + newNickname);
        }

        member.updateNickname(newNickname.nickname());
    }


    @Transactional
    public void editMemberPassword(EditPasswordReqDTO newPassword) {
        UserDetails memberDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        getMember(memberDetails.getUsername())
                .updatePassword(passwordEncoder.encode(newPassword.password()));
    }

    public Member getMember(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
                () -> new Exception404("존재하지 않는 회원입니다.")
        );
    }
}
