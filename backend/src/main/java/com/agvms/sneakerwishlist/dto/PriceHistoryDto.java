package com.agvms.sneakerwishlist.dto;

import com.agvms.sneakerwishlist.entity.PriceHistory;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PriceHistoryDto(
        @Schema(example = "220.00") BigDecimal price,
        LocalDateTime checkedAt
) {

    public static PriceHistoryDto from(PriceHistory priceHistory) {
        return new PriceHistoryDto(priceHistory.getPrice(), priceHistory.getCheckedAt());
    }
}
