package com.timcooki.jnuwiki.domain.docsRequest.mapper;


import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docsRequest.dto.request.NewWriteReqDTO;
import com.timcooki.jnuwiki.domain.docsRequest.dto.request.EditWriteReqDTO;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequest;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.EditReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.NewReadResDTO;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface DocsRequestMapper {
    @Mapping(target = "docs", ignore = true) // 생성 요청이기 때문에 docs -> null 이므로 null 주입
    DocsRequest newDTOToEntity(NewWriteReqDTO newWriteReqDTO, Member docsRequestedBy);
    DocsRequest editDTOToEntity(EditWriteReqDTO editWriteReqDto, Docs docs, Member docsRequestedBy);

    @Mapping(target = "docsId", expression = "java(docsRequest.getDocs().getDocsId())")
    EditReadResDTO editEntityToDTO(DocsRequest docsRequest);
    NewReadResDTO newEntityToDTO(DocsRequest docsRequest);
}