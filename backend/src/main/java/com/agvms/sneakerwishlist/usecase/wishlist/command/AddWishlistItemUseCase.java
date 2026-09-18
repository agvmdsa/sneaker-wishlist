package com.agvms.sneakerwishlist.usecase.wishlist.command;

import com.agvms.sneakerwishlist.dto.WishlistItemCreateDto;
import com.agvms.sneakerwishlist.dto.WishlistItemDto;
import com.agvms.sneakerwishlist.entity.Brand;
import com.agvms.sneakerwishlist.entity.WishlistItem;
import com.agvms.sneakerwishlist.entity.WishlistStatus;
import com.agvms.sneakerwishlist.repository.BrandRepository;
import com.agvms.sneakerwishlist.repository.WishlistItemRepository;
import com.agvms.sneakerwishlist.usecase.CommandHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddWishlistItemUseCase implements CommandHandler<AddWishlistItemCommand, WishlistItemDto> {

    private final WishlistItemRepository wishlistItemRepository;
    private final BrandRepository brandRepository;

    public AddWishlistItemUseCase(WishlistItemRepository wishlistItemRepository, BrandRepository brandRepository) {
        this.wishlistItemRepository = wishlistItemRepository;
        this.brandRepository = brandRepository;
    }

    @Override
    @Transactional
    public WishlistItemDto execute(AddWishlistItemCommand command) {
        WishlistItemCreateDto dto = command.dto();
        return wishlistItemRepository.findByExternalSneakerIdAndSizeAndDeletedAtIsNull(dto.externalSneakerId(), dto.size())
                .map(WishlistItemDto::from)
                .orElseGet(() -> createItem(dto)); // idempotent
    }

    private WishlistItemDto createItem(WishlistItemCreateDto dto) {
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
}
