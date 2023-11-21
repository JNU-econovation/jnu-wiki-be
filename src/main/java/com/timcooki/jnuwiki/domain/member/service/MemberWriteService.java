package com.timcooki.jnuwiki.domain.member.service;

import com.timcooki.jnuwiki.domain.member.DTO.request.*;
import com.timcooki.jnuwiki.domain.member.DTO.response.LoginResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.WrapLoginResDTO;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.domain.member.entity.MemberRole;
import com.timcooki.jnuwiki.domain.member.repository.MemberRepository;
import com.timcooki.jnuwiki.domain.security.entity.RefreshToken;
import com.timcooki.jnuwiki.domain.security.service.RefreshTokenService;
import com.timcooki.jnuwiki.util.ApiUtils;
import com.timcooki.jnuwiki.util.CookieUtil;
import com.timcooki.jnuwiki.util.JwtUtil.JwtUtil;
import com.timcooki.jnuwiki.util.errors.exception.Exception400;
import com.timcooki.jnuwiki.util.errors.exception.Exception404;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    @Value("${jwt.secret}")
    private String secretKey;


    public WrapLoginResDTO login(HttpServletResponse response, LoginReqDTO loginReqDTO) {
        String email = loginReqDTO.email();
        String password = loginReqDTO.password();

        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new Exception404("존재하지 않는 회원입니다.")
        );

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new Exception400("패스워드가 잘못입력되었습니다 ");
        }

        Long memberId = member.getMemberId();
        String memberRole = member.getRole().toString();
        String token = JwtUtil.createJwt(email, memberRole, secretKey);

        // header 생성
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(member, secretKey);
        Long expiration = JwtUtil.getExpiration(token.split(" ")[1], secretKey);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, token);
        log.info("refreshToken : {}", refreshToken.getToken());

        CookieUtil.addCookie(response, "refresh-token", refreshToken.getToken(), expiration.intValue());

        return WrapLoginResDTO.builder()
                .headers(httpHeaders)
                .body(LoginResDTO.builder()
                        .id(memberId)
                        .role(memberRole)
                        .expiration(expiration)
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
    public void editInfo(EditReqDTO editReqDTO) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails memberDetails = (UserDetails) principal;

        Member member = memberRepository.findByEmail(memberDetails.getUsername()).orElseThrow(
                () -> new Exception404("존재하지 않는 회원입니다.")
        );

        if (memberRepository.existsByNickName(memberDetails.getUsername())) {
            throw new Exception400("중복된 닉네임 입니다.:nickname");
        }

        member.update(editReqDTO.nickname(), passwordEncoder.encode(editReqDTO.password()));
    }

    @Transactional
    public void editMemberNickname(EditNicknameReqDTO newNickname) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails memberDetails = (UserDetails) principal;

        Member member = memberRepository.findByEmail(memberDetails.getUsername()).orElseThrow(
                () -> new Exception404("존재하지 않는 회원입니다.")
        );

        if (memberRepository.existsByNickName(memberDetails.getUsername())) {
            throw new Exception400("중복된 닉네임 입니다.:" + newNickname);
        }

        member.updateNickname(newNickname.nickname());
    }


    @Transactional
    public void editMemberPassword(EditPasswordReqDTO newPassword) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails memberDetails = (UserDetails) principal;

        Member member = memberRepository.findByEmail(memberDetails.getUsername()).orElseThrow(
                () -> new Exception404("존재하지 않는 회원입니다.")
        );

        member.updatePassword(passwordEncoder.encode(newPassword.password()));
    }
}
