package com.agvms.sneakerwishlist.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI sneakerWishlistOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sneaker Wishlist API")
                        .description("Personal sneaker wishlist backend — proxies KicksDB's sneaker catalog and manages a personal wishlist collection.")
                        .version("v1"));
    }
}
