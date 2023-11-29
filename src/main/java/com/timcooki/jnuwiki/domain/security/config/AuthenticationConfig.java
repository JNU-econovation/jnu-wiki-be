package com.timcooki.jnuwiki.domain.security.config;

import com.timcooki.jnuwiki.domain.security.service.MemberSecurityService;
import com.timcooki.jnuwiki.util.errors.exception.Exception401;
import com.timcooki.jnuwiki.util.errors.exception.Exception403;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Slf4j
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class AuthenticationConfig {

    private final MemberSecurityService memberSecurityService;

    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http.addFilter(new JwtFilter(authenticationManager));
        }
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   @Autowired @Qualifier("handlerExceptionResolver") final HandlerExceptionResolver resolver) throws Exception {
        return httpSecurity
                .httpBasic()
                .disable()

                .csrf()
                .disable()

                .cors()
                .configurationSource(configurationSource())

                .and()
                .apply(new CustomSecurityFilterManager())

                .and()
                .exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
                    log.info("{}, {}", request.getRemoteAddr(), authException.getMessage());
                    resolver.resolveException(request, response, null, new Exception401("인증되지 않은 사용자입니다."));
                })

                .and()
                .exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) -> {
                    log.info("{}, {}", request.getRemoteAddr(), accessDeniedException.getMessage());
                    resolver.resolveException(request, response, null, new Exception403("접근 권한이 없습니다."));
                })

                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/docs/**").permitAll()

                .antMatchers("/members/join", "/members/login", "/members/access-token", "/members/check/**")
                .permitAll()

                .antMatchers("/swagger-ui/**", "/v3/**")
                .permitAll()

                .antMatchers("/admin/**")
                .hasAuthority("ADMIN")

                .anyRequest()
                .authenticated()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .build();
    }

    @Bean
    CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("Authorization");
        configuration.addExposedHeader("Set-Cookie");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
