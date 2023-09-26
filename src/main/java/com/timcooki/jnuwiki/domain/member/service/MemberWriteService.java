package com.timcooki.jnuwiki.domain.member.service;

import com.timcooki.jnuwiki.domain.member.DTO.request.*;
import com.timcooki.jnuwiki.domain.member.DTO.response.LoginResDTO;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class MemberWriteService {
    private final Validator validator;
    private final MemberRepository memberRepository;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secretKey;



    public ResponseEntity<?> login(LoginReqDTO loginReqDTO) {
        String email = loginReqDTO.email();
        String password = loginReqDTO.password();

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

            RefreshToken refreshToken = refreshTokenService.createRefreshToken(member);
            Long memberId = member.getMemberId();
            String memberRole = member.getRole().toString();

            String token = JwtUtil.createJwt(email, memberRole, secretKey);

            // header 생성
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set(HttpHeaders.AUTHORIZATION, token);
            httpHeaders.set(HttpHeaders.SET_COOKIE, refreshToken.getToken());

            // DTO 생성
            LoginResDTO loginResDTO = LoginResDTO.builder()
                    .id(memberId)
                    .role(memberRole)
                    .build();

            return ResponseEntity.ok()
                    .headers(httpHeaders)
                    .body(ApiUtils.success(loginResDTO));

        } else {
            return ResponseEntity.badRequest().body(ApiUtils.error("이메일과 비밀번호를 확인해주세요", HttpStatus.UNAUTHORIZED));
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
        if (existEmail(email)) {
            throw new Exception400("존재하는 이메일 입니다.");
        }
    }

    private void validEmail(String email) {
        // 이메일 형식 검증
        if (!validator.isValidEmail(email)) {
            throw new Exception400("이메일 형식으로 작성해주세요.:" + email);
        }
    }

    private boolean existEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    public boolean isPresentNickName(CheckNicknameReqDTO checkNicknameReqDTO) {

        return memberRepository.findByNickName(checkNicknameReqDTO.nickname()).isPresent();
    }

    public boolean isPresentEmail(CheckEmailReqDTO checkEmailReqDTO) {

        validEmail(checkEmailReqDTO.email());
        return memberRepository.findByEmail(checkEmailReqDTO.email()).isPresent();
    }

    @Transactional
    public void editInfo(EditReqDTO editReqDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getDetails();

        Member member = memberRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new Exception404("존재하지 않는 회원입니다.")
        );

        if (memberRepository.findByNickName(editReqDTO.nickname()).isPresent()) {
            throw new Exception400("중복된 닉네임 입니다.:nickname");
        }
        validPassword(editReqDTO.password());


        member.update(editReqDTO.nickname(), passwordEncoder.encode(editReqDTO.password()));
    }
}
