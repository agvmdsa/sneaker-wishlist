package com.agvms.sneakerwishlist.service;

import com.agvms.sneakerwishlist.dto.WishlistItemCreateDto;
import com.agvms.sneakerwishlist.dto.WishlistItemDto;
import com.agvms.sneakerwishlist.dto.WishlistItemUpdateDto;
import com.agvms.sneakerwishlist.entity.Brand;
import com.agvms.sneakerwishlist.entity.WishlistItem;
import com.agvms.sneakerwishlist.entity.WishlistStatus;
import com.agvms.sneakerwishlist.repository.BrandRepository;
import com.agvms.sneakerwishlist.repository.WishlistItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

    @Transactional(readOnly = true)
    public Page<WishlistItemDto> list(WishlistStatus status, String tag, Pageable pageable) {
        List<WishlistStatus> statuses = status != null
                ? List.of(status)
                : List.of(WishlistStatus.WANT, WishlistStatus.OWNED);

        Page<WishlistItem> page = (tag != null)
                ? wishlistItemRepository.findByStatusInAndDeletedAtIsNullAndTagsNameIgnoreCase(statuses, tag, pageable)
                : wishlistItemRepository.findByStatusInAndDeletedAtIsNull(statuses, pageable);

        return page.map(WishlistItemDto::from);
    }

    @Transactional
    public WishlistItemDto updateItem(Long id, WishlistItemUpdateDto dto) {
        WishlistItem item = wishlistItemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wishlist item not found: " + id));

        if (dto.size() != null) {
            item.setSize(dto.size());
        }
        if (dto.notes() != null) {
            item.setNotes(dto.notes());
        }
        return WishlistItemDto.from(item);
    }
}
