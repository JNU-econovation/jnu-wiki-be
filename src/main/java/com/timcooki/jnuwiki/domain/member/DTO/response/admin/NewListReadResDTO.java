package com.timcooki.jnuwiki.domain.member.DTO.response.admin;

import lombok.Builder;

import java.util.List;

public record NewListReadResDTO(
        List<NewReadResDTO> createdRequestList
) {
    @Builder
    public NewListReadResDTO {

    }
}