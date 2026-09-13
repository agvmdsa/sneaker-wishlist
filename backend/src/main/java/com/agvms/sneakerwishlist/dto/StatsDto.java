package com.agvms.sneakerwishlist.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record StatsDto(
        @Schema(example = "12") long totalActiveItems,
        @Schema(example = "540.00") BigDecimal totalSpent,
        @Schema(example = "1200.00") BigDecimal estimatedWishlistValue,
        @Schema(example = "Jordan") String mostFrequentBrand
) {
}
