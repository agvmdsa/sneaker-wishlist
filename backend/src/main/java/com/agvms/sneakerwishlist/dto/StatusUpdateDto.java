package com.agvms.sneakerwishlist.dto;

import com.agvms.sneakerwishlist.entity.WishlistStatus;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record StatusUpdateDto(
        @NotNull WishlistStatus status,
        BigDecimal pricePaid
) {
}
