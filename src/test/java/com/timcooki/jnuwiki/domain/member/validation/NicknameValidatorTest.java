package com.timcooki.jnuwiki.domain.member.validation;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.timcooki.jnuwiki.domain.member.DTO.request.JoinReqDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class NicknameValidatorTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper om;

    @DisplayName("닉네임 자리수 실패 테스트")
    @Test
    public void nickname_8자리_테스트() throws Exception{
        // given
        JoinReqDTO joinReqDTO = JoinReqDTO.builder()
                .email("dsaf@naver.com")
                .nickName("11asdfasdfsadfsa")
                .password("asdfasdf")
                .build();


        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/members/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(joinReqDTO))
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString(Charset.forName("UTF-8"));
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpectAll(
                status().is4xxClientError(), jsonPath("$.error.status").value("400"));

    }
}
