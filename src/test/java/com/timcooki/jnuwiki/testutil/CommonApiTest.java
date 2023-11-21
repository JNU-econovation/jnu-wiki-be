package com.timcooki.jnuwiki.testutil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timcooki.jnuwiki.domain.security.config.AuthenticationConfig;
import com.timcooki.jnuwiki.domain.security.config.JwtFilter;
import com.timcooki.jnuwiki.util.errors.GlobalExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest({
        AuthenticationConfig.class,
        JwtFilter.class,
        GlobalExceptionHandler.class
})
public abstract class CommonApiTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper om;

}
