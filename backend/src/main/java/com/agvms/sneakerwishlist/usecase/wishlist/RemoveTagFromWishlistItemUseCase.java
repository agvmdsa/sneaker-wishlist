package com.agvms.sneakerwishlist.usecase.wishlist;

import com.agvms.sneakerwishlist.entity.WishlistItem;
import com.agvms.sneakerwishlist.repository.WishlistItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RemoveTagFromWishlistItemUseCase {

    private final WishlistItemRepository wishlistItemRepository;

    public RemoveTagFromWishlistItemUseCase(WishlistItemRepository wishlistItemRepository) {
        this.wishlistItemRepository = wishlistItemRepository;
    }

    @Transactional
    public void execute(Long itemId, Long tagId) {
        WishlistItem item = wishlistItemRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wishlist item not found: " + itemId));
        item.getTags().removeIf(tag -> tag.getId().equals(tagId));
    }
}
