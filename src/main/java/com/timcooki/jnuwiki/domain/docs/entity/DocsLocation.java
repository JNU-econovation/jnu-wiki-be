package com.timcooki.jnuwiki.domain.docs.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
@Table(name = "DOCS_LOCATION")
public class DocsLocation {
    @Id
    @GeneratedValue
    @Column(name = "docs_location_id")
    private Long id;
    private Double lat;
    private Double lng;

    @Builder
    public DocsLocation(Double lat, Double lng){
        this.lat = lat;
        this.lng = lng;
    }
}
