package com.timcooki.jnuwiki.domain.docs.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Getter
@Embeddable
public class DocsLocation {
    private Double lat;
    private Double lng;
}
