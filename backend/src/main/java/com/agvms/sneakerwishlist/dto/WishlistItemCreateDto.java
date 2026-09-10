package com.agvms.sneakerwishlist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record WishlistItemCreateDto(
        @NotBlank String externalSneakerId,
        @NotBlank String name,
        @NotBlank String brand,
        String imageUrl,
        @PositiveOrZero BigDecimal retailPrice,
        @NotBlank String size
) {
}
