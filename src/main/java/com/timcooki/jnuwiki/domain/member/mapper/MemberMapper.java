package com.timcooki.jnuwiki.domain.member.mapper;

import com.timcooki.jnuwiki.domain.member.DTO.request.JoinReqDTO;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.domain.member.entity.MemberRole;
import org.mapstruct.Mapper;

@Mapper
public interface MemberMapper {
    //MemberMapper INSTANCE = Mappers.getMapper( MemberMapper.class );


    Member toEntity(JoinReqDTO joinReqDTO, MemberRole memberRole);
}
