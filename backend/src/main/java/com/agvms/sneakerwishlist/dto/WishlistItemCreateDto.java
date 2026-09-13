package com.agvms.sneakerwishlist.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record WishlistItemCreateDto(
        @NotBlank @Schema(example = "5ef5e6fd-465e-400d-8b7a-6cd9f6862bb0") String externalSneakerId,
        @NotBlank @Schema(example = "Jordan 4 Retro Rare Air (White Lettering)") String name,
        @NotBlank @Schema(example = "Jordan") String brand,
        @Schema(example = "https://images.stockx.com/images/Air-Jordan-4-Retro-Rare-Air-Product.jpg") String imageUrl,
        @PositiveOrZero @Schema(example = "220") BigDecimal retailPrice,
        @NotBlank @Schema(example = "10") String size
) {
}
