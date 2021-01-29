package com.steve.BookApi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    ObjectMapper mapper() {
        return new ObjectMapper();
    }
}
