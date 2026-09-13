package com.agvms.sneakerwishlist.dto;

import com.agvms.sneakerwishlist.entity.Tag;
import com.agvms.sneakerwishlist.entity.WishlistItem;
import com.agvms.sneakerwishlist.entity.WishlistStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record WishlistItemDto(
        @Schema(example = "1") Long id,
        @Schema(example = "5ef5e6fd-465e-400d-8b7a-6cd9f6862bb0") String externalSneakerId,
        @Schema(example = "Jordan 4 Retro Rare Air (White Lettering)") String name,
        @Schema(example = "Jordan") String brand,
        @Schema(example = "https://images.stockx.com/images/Air-Jordan-4-Retro-Rare-Air-Product.jpg") String imageUrl,
        @Schema(example = "220") BigDecimal retailPrice,
        @Schema(example = "10") String size,
        @Schema(example = "WANT") WishlistStatus status,
        @Schema(example = "180.00") BigDecimal pricePaid,
        @Schema(example = "Bought as a birthday gift") String notes,
        @Schema(example = "false") boolean priceDropDetected,
        LocalDateTime addedAt,
        Set<String> tags
) {

    public static WishlistItemDto from(WishlistItem item) {
        return new WishlistItemDto(
                item.getId(),
                item.getExternalSneakerId(),
                item.getName(),
                item.getBrand().getName(),
                item.getImageUrl(),
                item.getRetailPrice(),
                item.getSize(),
                item.getStatus(),
                item.getPricePaid(),
                item.getNotes(),
                item.isPriceDropDetected(),
                item.getAddedAt(),
                item.getTags().stream().map(Tag::getName).collect(Collectors.toSet())
        );
    }
}
