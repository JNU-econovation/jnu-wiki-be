package com.timcooki.jnuwiki.domain.docsRequest.mapper;


import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docsRequest.dto.request.NewWriteReqDTO;
import com.timcooki.jnuwiki.domain.docsRequest.dto.request.EditWriteReqDTO;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequest;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.EditReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.NewReadResDTO;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface DocsRequestMapper {
    @Mapping(target = "docs", ignore = true) // 생성 요청이기 때문에 docs -> null 이므로 null 주입
    @Mapping(target = "docsRequestCategory", source = "docsRequestCategory")
    DocsRequest newDTOToEntity(NewWriteReqDTO newWriteReqDTO, Member docsRequestedBy, DocsCategory docsRequestCategory);

    @Mapping(target = "docsRequestCategory", source = "docsRequestCategory")
    DocsRequest editDTOToEntity(EditWriteReqDTO editWriteReqDto, Docs docs, Member docsRequestedBy, DocsCategory docsRequestCategory);

    @Mapping(target = "docsId", expression = "java(docsRequest.getDocs().getDocsId())")
    @Mapping(target = "docsRequestCategory", source = "docsRequestCategory")
    EditReadResDTO editEntityToDTO(DocsRequest docsRequest, String docsRequestCategory);

    @Mapping(target = "docsRequestCategory", source = "docsRequestCategory")
    NewReadResDTO newEntityToDTO(DocsRequest docsRequest, String docsRequestCategory);
}