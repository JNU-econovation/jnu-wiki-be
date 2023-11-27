package com.timcooki.jnuwiki.domain.member.DTO.response;

import lombok.Builder;

@Builder
public record AccessTokenResDTO(Long accessTokenExpiration,
                                String accessTokenFormattedExpiration) {

}
