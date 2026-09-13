package com.agvms.sneakerwishlist.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record SneakerSummaryDto(
        @Schema(example = "5ef5e6fd-465e-400d-8b7a-6cd9f6862bb0") String id,
        @Schema(example = "Jordan 4 Retro Rare Air (White Lettering)") String title,
        @Schema(example = "Jordan") String brand,
        @Schema(example = "https://images.stockx.com/images/Air-Jordan-4-Retro-Rare-Air-Product.jpg") String imageUrl,
        @Schema(example = "220") BigDecimal avgPrice,
        @Schema(example = "sneakers") String productType
) {
}
