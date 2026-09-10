package com.agvms.sneakerwishlist.service;

import com.agvms.sneakerwishlist.dto.WishlistItemCreateDto;
import com.agvms.sneakerwishlist.entity.Brand;
import com.agvms.sneakerwishlist.entity.WishlistItem;
import com.agvms.sneakerwishlist.entity.WishlistStatus;
import com.agvms.sneakerwishlist.repository.BrandRepository;
import com.agvms.sneakerwishlist.repository.WishlistItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishlistService {

    private final WishlistItemRepository wishlistItemRepository;
    private final BrandRepository brandRepository;

    public WishlistService(WishlistItemRepository wishlistItemRepository, BrandRepository brandRepository) {
        this.wishlistItemRepository = wishlistItemRepository;
        this.brandRepository = brandRepository;
    }

    @Transactional
    public WishlistItem addItem(WishlistItemCreateDto dto) {
        Brand brand = brandRepository.findByNameIgnoreCase(dto.brand())
                .orElseGet(() -> brandRepository.save(new Brand(dto.brand())));

        WishlistItem item = new WishlistItem(
                dto.externalSneakerId(),
                dto.name(),
                brand,
                dto.imageUrl(),
                dto.retailPrice(),
                dto.size(),
                WishlistStatus.WANT
        );
        return wishlistItemRepository.save(item);
    }
}
