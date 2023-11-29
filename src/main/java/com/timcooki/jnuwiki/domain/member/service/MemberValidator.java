package com.timcooki.jnuwiki.domain.member.service;

import com.timcooki.jnuwiki.domain.member.repository.MemberRepository;
import com.timcooki.jnuwiki.util.errors.exception.Exception400;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberValidator {
    private final MemberRepository memberRepository;

    public void validateEmailDuplication(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new Exception400("존재하는 이메일 입니다.");
        }
    }

    public void validateNicknameDuplication(String nickname) {
        if (memberRepository.existsByNickName(nickname)) {
            throw new Exception400("동일한 닉네임이 존재합니다.: " + nickname);
        }
    }
}
