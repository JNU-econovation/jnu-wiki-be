package com.timcooki.jnuwiki.domain.member.DTO.response.admin;

import lombok.Builder;

import java.util.List;
//
public record EditListReadResDTO(
        List<EditReadResDTO> modifiedRequestList,
        int totalPages
) {
    @Builder
    public EditListReadResDTO {

    }
}
