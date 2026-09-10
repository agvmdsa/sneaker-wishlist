package com.agvms.sneakerwishlist.dto;

import com.agvms.sneakerwishlist.entity.Tag;
import com.agvms.sneakerwishlist.entity.WishlistItem;
import com.agvms.sneakerwishlist.entity.WishlistStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record WishlistItemDto(
        Long id,
        String externalSneakerId,
        String name,
        String brand,
        String imageUrl,
        BigDecimal retailPrice,
        String size,
        WishlistStatus status,
        BigDecimal pricePaid,
        String notes,
        boolean priceDropDetected,
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
