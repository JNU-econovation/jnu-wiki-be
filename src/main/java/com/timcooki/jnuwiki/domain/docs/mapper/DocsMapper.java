package com.timcooki.jnuwiki.domain.docs.mapper;

import com.timcooki.jnuwiki.domain.docs.DTO.response.SearchReadResDTO;
import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface DocsMapper {
    @Mapping(target="createdBy",source = "createdBy")
    SearchReadResDTO entityToDTO(Docs docs, String createdBy);
}
