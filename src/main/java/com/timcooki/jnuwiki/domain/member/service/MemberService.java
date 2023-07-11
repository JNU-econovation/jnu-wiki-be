package com.timcooki.jnuwiki.domain.member.service;

import com.timcooki.jnuwiki.domain.member.DTO.JoinReqDTO;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.domain.member.repository.MemberRepository;
import com.timcooki.jnuwiki.util.ApiUtils;
import com.timcooki.jnuwiki.util.JwtUtil.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final Validator validator;

    @Value("${jwt.secret}")
    private String secretKey;

    // 액세스 토큰 만료 시간 지정
    private final Long accessTokenExpiredMS = 1000 * 60 * 60L;

    public ResponseEntity<?> login(String email, String password) {

        // id, password 검증
        if (!validationMember(email, password)) {
            // TODO: Exception 변경
            throw new RuntimeException("맞는 아이디와 비밀번호가 아닙니다.");
        }
        String memberRole = memberRepository.findByEmail(email).get().getRole().toString();

        String token = JwtUtil.createJwt(email,memberRole, secretKey, accessTokenExpiredMS);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, token)
                .body(ApiUtils.success(null));
    }

    public ResponseEntity<?> join(JoinReqDTO joinReqDTO){

        // 이메일 형식 검증
        if (!validator.isValidEmail(joinReqDTO.email())){
            throw new IllegalArgumentException("이메일 형식으로 작성해주세요.:"+joinReqDTO.email());
        }

        // email 중복 확인
        if(existEmail(joinReqDTO.email())){
            throw new IllegalArgumentException("존재하는 이메일 입니다.");
        }

        // 비밀번호 형식 확인
        if(!validator.isValidPassword(joinReqDTO.password())){
            throw new IllegalArgumentException("비밀번호는 8~16자여야 하고 영문, 숫자, 특수문자가 포함되어야합니다.:"+joinReqDTO.password());
        }
        memberRepository.save(joinReqDTO.toEntity());

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    private boolean existEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    private boolean validationMember(String email, String password) {
        // id가 있는지 확인
        if (memberRepository.findByEmail(email).isEmpty()) {
            return false;
        }

        Member loginMember = memberRepository.findByEmail(email).get();

        // 아이디에 대응되는 비밀번호가 맞는지 확인
        return loginMember.getPassword().equals(password);
    }
}
