package com.timcooki.jnuwiki.domain.member.DTO.response.admin;

import lombok.Builder;

import java.util.List;

public record ModifiedFindAllReqDTO(
        List<ModifiedFindByIdReqDTO> modifiedRequestList
) {
    @Builder
    public ModifiedFindAllReqDTO {

    }
}
