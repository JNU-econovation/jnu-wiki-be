package com.timcooki.jnuwiki.domain.member.DTO.response.admin;

import lombok.Builder;
import java.util.List;

public record CreatedFindAllReqDTO(
        List<CreatedFindByIdReqDTO> createdRequestList
) {
    @Builder
    public CreatedFindAllReqDTO{

    }
}
