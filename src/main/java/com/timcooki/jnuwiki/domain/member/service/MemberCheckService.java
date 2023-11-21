package com.timcooki.jnuwiki.domain.member.service;

import com.timcooki.jnuwiki.domain.member.DTO.request.CheckEmailReqDTO;
import com.timcooki.jnuwiki.domain.member.DTO.request.CheckNicknameReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberCheckService {
    private final MemberValidator memberValidator;

    public void checkNickName(CheckNicknameReqDTO checkNicknameReqDTO) {
        memberValidator.validateNicknameDuplication(checkNicknameReqDTO.nickname());
    }

    public void checkEmail(CheckEmailReqDTO checkEmailReqDTO) {
        memberValidator.validateEmailDuplication(checkEmailReqDTO.email());
    }
}
