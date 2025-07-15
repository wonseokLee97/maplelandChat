package com.mapleland.chatservice.shared.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapleland.chatservice.shared.jwt.JwtAuthenticationFilter;
import com.mapleland.chatservice.shared.jwt.JwtAuthenticationProvider;
import com.mapleland.chatservice.shared.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final CorsConfig corsConfig;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(
            AuthenticationConfiguration authConfig,
            ObjectMapper objectMapper) throws Exception {

        return new JwtAuthenticationFilter(authConfig.getAuthenticationManager(), objectMapper);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           JwtAuthenticationFilter jwtAuthenticationFilter,
                                           JwtProvider jwtProvider) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource())) // CORS 허용
                // Authentication
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(form -> form.disable())
                .authenticationProvider(jwtAuthenticationProvider)

                // Authorization
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()    // 또는 .denyAll() 등 원하는 정책
                )
                .build(); // 모든 요청 허용
    }
}