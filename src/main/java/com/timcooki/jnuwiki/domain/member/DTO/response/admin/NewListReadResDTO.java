package com.timcooki.jnuwiki.domain.member.DTO.response.admin;

import lombok.Builder;
import java.util.List;
@Builder
public record NewListReadResDTO(
        List<NewReadResDTO> createdRequestList,
        int totalPages
) {
}
