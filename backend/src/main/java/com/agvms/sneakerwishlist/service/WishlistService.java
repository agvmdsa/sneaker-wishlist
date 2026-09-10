package com.agvms.sneakerwishlist.service;

import com.agvms.sneakerwishlist.dto.WishlistItemCreateDto;
import com.agvms.sneakerwishlist.dto.WishlistItemDto;
import com.agvms.sneakerwishlist.entity.Brand;
import com.agvms.sneakerwishlist.entity.WishlistItem;
import com.agvms.sneakerwishlist.entity.WishlistStatus;
import com.agvms.sneakerwishlist.repository.BrandRepository;
import com.agvms.sneakerwishlist.repository.WishlistItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WishlistService {

    private final WishlistItemRepository wishlistItemRepository;
    private final BrandRepository brandRepository;

    public WishlistService(WishlistItemRepository wishlistItemRepository, BrandRepository brandRepository) {
        this.wishlistItemRepository = wishlistItemRepository;
        this.brandRepository = brandRepository;
    }

    @Transactional
    public WishlistItemDto addItem(WishlistItemCreateDto dto) {
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
        return WishlistItemDto.from(wishlistItemRepository.save(item));
    }

    @Transactional(readOnly = true)
    public Set<String> findAlreadyInCollection(List<String> externalSneakerIds) {
        return wishlistItemRepository.findByExternalSneakerIdInAndDeletedAtIsNull(externalSneakerIds)
                .stream()
                .map(WishlistItem::getExternalSneakerId)
                .collect(Collectors.toSet());
    }
}
