package com.timcooki.jnuwiki.domain.docsRequest.entity;

public enum DocsCategory {
    /*
    카페 음식점
    학교시설
    복사집
    편의점
    약국
     */
    CAFE("카페"),
    SCHOOL("학교시설"),
    COPY("복사집"),
    CONV("편의점"),
    PHAR("약국");

    public String name;


    DocsCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
