package com.agvms.sneakerwishlist.dto;

import com.agvms.sneakerwishlist.entity.WishlistStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record StatusUpdateDto(
        @NotNull @Schema(example = "OWNED") WishlistStatus status,
        @Schema(example = "180.00") BigDecimal pricePaid
) {
}
