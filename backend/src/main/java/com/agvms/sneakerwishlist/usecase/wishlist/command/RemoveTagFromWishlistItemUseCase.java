package com.agvms.sneakerwishlist.usecase.wishlist.command;

import com.agvms.sneakerwishlist.entity.WishlistItem;
import com.agvms.sneakerwishlist.repository.WishlistItemRepository;
import com.agvms.sneakerwishlist.usecase.CommandHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RemoveTagFromWishlistItemUseCase implements CommandHandler<RemoveTagFromWishlistItemCommand, Void> {

    private final WishlistItemRepository wishlistItemRepository;

    public RemoveTagFromWishlistItemUseCase(WishlistItemRepository wishlistItemRepository) {
        this.wishlistItemRepository = wishlistItemRepository;
    }

    @Override
    @Transactional
    public Void execute(RemoveTagFromWishlistItemCommand command) {
        WishlistItem item = wishlistItemRepository.findById(command.itemId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wishlist item not found: " + command.itemId()));
        item.getTags().removeIf(tag -> tag.getId().equals(command.tagId()));
        return null;
    }
}
