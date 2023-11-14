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
import com.timcooki.jnuwiki.util.JwtUtil.JwtUtil;
import com.timcooki.jnuwiki.util.errors.exception.Exception400;
import com.timcooki.jnuwiki.util.errors.exception.Exception404;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
    private final Validator validator;
    private final MemberRepository memberRepository;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secretKey;



    public WrapLoginResDTO<?> login(LoginReqDTO loginReqDTO) {
        String email = loginReqDTO.email();
        String password = loginReqDTO.password();
        log.info("email : {}", email);
        log.info("password : {}", password);

        validEmail(email);
        validPassword(password);

        // AuthenticationManger에게 인증 진행 위임
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        // 인증되었다면,
        if (authentication.isAuthenticated()) {
            // 리프레시 토큰 발급
            Member member = memberRepository.findByEmail(email).orElseThrow(
                    () -> new Exception404("존재하지 않는 회원입니다.")
            );

            RefreshToken refreshToken = refreshTokenService.createRefreshToken(member, secretKey);
            Long memberId = member.getMemberId();
            String memberRole = member.getRole().toString();

            String token = JwtUtil.createJwt(email, memberRole, secretKey);
            // header 생성
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set(HttpHeaders.AUTHORIZATION, token);
            httpHeaders.set(HttpHeaders.SET_COOKIE, refreshToken.getToken());

            Long expiration = JwtUtil.getExpiration(token.split(" ")[1], secretKey);

            // DTO 생성
            LoginResDTO loginResDTO = LoginResDTO.builder()
                    .id(memberId)
                    .role(memberRole)
                    .expiration(expiration)
                    .build();
            return WrapLoginResDTO.builder()
                    .status(HttpStatus.OK)
                    .headers(httpHeaders)
                    .body(ApiUtils.success(loginResDTO))
                    .build();

        } else {// 인증 오류시
            return WrapLoginResDTO.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiUtils.error("이메일과 비밀번호를 확인해주세요.", HttpStatus.BAD_REQUEST))
                    .build();
        }

    }


    public void join(JoinReqDTO joinReqDTO) {
        validEmail(joinReqDTO.email());
        duplicateCheckEmail(joinReqDTO.email());
        validPassword(joinReqDTO.password());

        Member member = Member.builder()
                .email(joinReqDTO.email())
                .nickName(joinReqDTO.nickName())
                .role(MemberRole.USER)
                .password(passwordEncoder.encode(joinReqDTO.password()))
                .build();

        memberRepository.save(member);
    }

    private void validPassword(String password) {
        // 비밀번호 형식 확인
        if (!validator.isValidPassword(password)) {
            throw new Exception400("비밀번호는 8~16자여야 하고 영문, 숫자, 특수문자가 포함되어야합니다.:" + password);
        }
    }

    private void duplicateCheckEmail(String email) {
        // email 중복 확인
        if (memberRepository.existsByEmail(email)) {
            throw new Exception400("존재하는 이메일 입니다.");
        }
    }

    private void validEmail(String email) {
        // 이메일 형식 검증
        if (!validator.isValidEmail(email)) {
            throw new Exception400("이메일 형식으로 작성해주세요.:" + email);
        }
    }

    public boolean isPresentNickName(CheckNicknameReqDTO checkNicknameReqDTO) {
        return memberRepository.existsByNickName(checkNicknameReqDTO.nickname());
    }

    public boolean isPresentEmail(CheckEmailReqDTO checkEmailReqDTO) {
        validEmail(checkEmailReqDTO.email());
        return memberRepository.existsByEmail(checkEmailReqDTO.email());
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
        validPassword(editReqDTO.password());

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

        validPassword(newPassword.password());

        member.updatePassword(passwordEncoder.encode(newPassword.password()));
    }
}
