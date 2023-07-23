package com.timcooki.jnuwiki.domain.docs.mapper;

import com.timcooki.jnuwiki.domain.docs.DTO.response.SearchReadResDTO;
import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import org.mapstruct.Mapper;

@Mapper
public interface DocsMapper {
    SearchReadResDTO entityToDTO(Docs docs);
}
