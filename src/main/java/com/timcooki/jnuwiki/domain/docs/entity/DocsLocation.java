package com.timcooki.jnuwiki.domain.docs.entity;

import javax.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Embeddable
@RequiredArgsConstructor
public class DocsLocation {
    private Double lat;
    private Double lng;

    @Builder
    public DocsLocation(Double lat, Double lng){
        this.lat = lat;
        this.lng = lng;
    }
}
