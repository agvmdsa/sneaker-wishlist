package com.agvms.sneakerwishlist.dto;

import java.math.BigDecimal;

public record StatsDto(
        long totalActiveItems,
        BigDecimal totalSpent,
        BigDecimal estimatedWishlistValue,
        String mostFrequentBrand
) {
}
