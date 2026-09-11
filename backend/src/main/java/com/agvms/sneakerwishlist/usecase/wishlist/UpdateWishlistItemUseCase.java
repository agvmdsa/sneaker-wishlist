package com.agvms.sneakerwishlist.usecase.wishlist;

import com.agvms.sneakerwishlist.dto.WishlistItemDto;
import com.agvms.sneakerwishlist.dto.WishlistItemUpdateDto;
import com.agvms.sneakerwishlist.entity.WishlistItem;
import com.agvms.sneakerwishlist.repository.WishlistItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UpdateWishlistItemUseCase {

    private final WishlistItemRepository wishlistItemRepository;

    public UpdateWishlistItemUseCase(WishlistItemRepository wishlistItemRepository) {
        this.wishlistItemRepository = wishlistItemRepository;
    }

    @Transactional
    public WishlistItemDto execute(Long id, WishlistItemUpdateDto dto) {
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
