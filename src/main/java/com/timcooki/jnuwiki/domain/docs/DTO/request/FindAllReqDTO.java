package com.timcooki.jnuwiki.domain.docs.DTO.request;

import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import lombok.Builder;

public record FindAllReqDTO(
        Double rightLat,
        Double rightLng,
        Double leftLat,
        Double leftLng
) {
    @Builder
    public FindAllReqDTO{

    }
}
