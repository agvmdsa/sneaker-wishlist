package com.agvms.sneakerwishlist.usecase.wishlist;

import com.agvms.sneakerwishlist.entity.WishlistItem;
import com.agvms.sneakerwishlist.repository.WishlistItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class DeleteWishlistItemUseCase {

    private final WishlistItemRepository wishlistItemRepository;

    public DeleteWishlistItemUseCase(WishlistItemRepository wishlistItemRepository) {
        this.wishlistItemRepository = wishlistItemRepository;
    }

    @Transactional
    public void execute(Long id) {
        WishlistItem item = wishlistItemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wishlist item not found: " + id));
        item.setDeletedAt(LocalDateTime.now());
    }
}
