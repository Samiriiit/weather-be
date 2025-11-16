package com.sapient.sde.weather_predictor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration // Singalton
// Proxy Design Pattern
// The SecurityFilterChain bean acts like a gatekeeper for HTTP requests.
// Before requests hit backend endpoints (/weather-prediction), Spring Security intercepts them.
// CorsConfigurationSource decides whether the request origin, headers, and methods are allowed.
// If not allowed → request is rejected (access control).
//
// If allowed → request proceeds to your controller.

public class CorsConfig {
    @Value("${app.cors.allowed-origins:http://54.234.127.44:30469/}")
    private String allowedOrigins;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        List<String> origins = List.of(allowedOrigins.split(","));
        // config.setAllowedOrigins(origins);
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // include OPTIONS
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization", "Content-Type"));
        // config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of("*"));
        config.setAllowCredentials(false);  // must be false when using "*"

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
