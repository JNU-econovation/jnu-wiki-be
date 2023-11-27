package com.timcooki.jnuwiki.domain.member.DTO.response.admin;

import com.timcooki.jnuwiki.domain.member.DTO.response.AccessTokenResDTO;
import lombok.Builder;

@Builder
public record WrapAccessTokenResDTO(
        String accessToken,
        AccessTokenResDTO accessTokenResDTO
) {
}
