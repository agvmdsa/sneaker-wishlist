package com.agvms.sneakerwishlist.usecase.wishlist;

import com.agvms.sneakerwishlist.dto.WishlistItemDto;
import com.agvms.sneakerwishlist.entity.WishlistItem;
import com.agvms.sneakerwishlist.repository.WishlistItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class GetWishlistItemUseCase {

    private final WishlistItemRepository wishlistItemRepository;

    public GetWishlistItemUseCase(WishlistItemRepository wishlistItemRepository) {
        this.wishlistItemRepository = wishlistItemRepository;
    }

    @Transactional(readOnly = true)
    public WishlistItemDto execute(Long id) {
        WishlistItem item = wishlistItemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wishlist item not found: " + id));

        return WishlistItemDto.from(item);
    }
}
