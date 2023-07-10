package com.timcooki.jnuwiki.domain.member.dto.response.admin;

import java.util.List;

public record ModifiedRequestFindAllDto(
        List<ModifiedRequestFindByIdDto> modifiedRequestList
) {
}
