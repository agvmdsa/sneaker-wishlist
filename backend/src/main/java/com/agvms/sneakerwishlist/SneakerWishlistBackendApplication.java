package com.agvms.sneakerwishlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SneakerWishlistBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SneakerWishlistBackendApplication.class, args);
	}

}
