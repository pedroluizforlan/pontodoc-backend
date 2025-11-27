package com.pedroluizforlan.pontodoc.config.security;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {
    
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // 1. Permite credenciais (cookies, headers de auth)
        config.setAllowCredentials(true);
        
        // 2. Define quais origens são permitidas (O SEU FRONTEND)
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        
        // 3. Define quais cabeçalhos são aceitos
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization"));
        
        // 4. Define quais métodos HTTP são aceitos
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
