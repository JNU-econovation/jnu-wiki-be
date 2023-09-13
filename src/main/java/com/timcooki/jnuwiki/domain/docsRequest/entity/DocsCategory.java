package com.timcooki.jnuwiki.domain.docsRequest.entity;

public enum DocsCategory {
    CAFE("카페"),
    SCHOOL("학교시설"),
    COPY("복사집"),
    CONV("편의점"),
    PHAR("약국"),
    // TODO: 음식점 추가했지만, 사이드 이펙트 조심할 것
    FOOD("음식점");

    private final String category;

    DocsCategory(String category){
        this.category = category;
    }

    public String getCategory(){
        return category;
    }

    public static DocsCategory nameOf(String name) {
        for (DocsCategory status : DocsCategory.values()) {
            if (status.getCategory().equals(name)) {
                return status;
            }
        }
        return null;
    }

}
