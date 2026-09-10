package com.agvms.sneakerwishlist;

import org.springframework.boot.SpringApplication;

public class TestSneakerWishlistBackendApplication {

	public static void main(String[] args) {
		SpringApplication.from(SneakerWishlistBackendApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
