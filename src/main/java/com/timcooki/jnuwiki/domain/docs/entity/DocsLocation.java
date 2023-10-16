package com.timcooki.jnuwiki.domain.docs.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

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
