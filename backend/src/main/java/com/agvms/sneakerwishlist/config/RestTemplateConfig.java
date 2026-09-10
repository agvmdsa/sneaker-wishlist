package com.agvms.sneakerwishlist.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder,
                                      @Value("${app.kicksdb.base-url}") String baseUrl,
                                      @Value("${app.kicksdb.api-key}") String apiKey) {
        return builder
                .rootUri(baseUrl)
                .defaultHeader("Authorization", apiKey)
                .build();
    }
}
