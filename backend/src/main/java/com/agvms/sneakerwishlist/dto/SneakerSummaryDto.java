package com.agvms.sneakerwishlist.dto;

import java.math.BigDecimal;

public record SneakerSummaryDto(
        String id,
        String title,
        String brand,
        String imageUrl,
        BigDecimal avgPrice,
        String productType
) {
}
