package com.timcooki.jnuwiki.domain.member.DTO.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record LoginReqDTO(
        @NotBlank
        @Email(message = "이메일 형식으로 작성해주세요.")
        String email,
        @NotBlank
        @Pattern(regexp = "^((?=.*\\d)(?=.*[a-zA-Z])(?=.*[\\W]).{8,20})$",
                message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8 ~ 16자의 비밀번호여야 합니다.")
        String password
) {
}
