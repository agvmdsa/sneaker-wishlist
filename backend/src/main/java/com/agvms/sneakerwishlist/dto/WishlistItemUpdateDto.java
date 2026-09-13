package com.agvms.sneakerwishlist.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record WishlistItemUpdateDto(
        @Schema(example = "10.5") String size,
        @Schema(example = "Bought as a birthday gift") String notes
) {
}
