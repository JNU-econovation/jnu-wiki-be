package com.timcooki.jnuwiki.domain.docsRequest.entity;

public enum DocsCategory {
    CAFE("카페"),
    SCHOOL("학교시설"),
    COPY("복사집"),
    CONV("편의점"),
    PHAR("약국");

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
