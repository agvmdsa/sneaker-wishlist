package com.agvms.sneakerwishlist.usecase.wishlist.command;

import com.agvms.sneakerwishlist.entity.WishlistItem;
import com.agvms.sneakerwishlist.repository.WishlistItemRepository;
import com.agvms.sneakerwishlist.usecase.CommandHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class DeleteWishlistItemUseCase implements CommandHandler<DeleteWishlistItemCommand, Void> {

    private final WishlistItemRepository wishlistItemRepository;

    public DeleteWishlistItemUseCase(WishlistItemRepository wishlistItemRepository) {
        this.wishlistItemRepository = wishlistItemRepository;
    }

    @Override
    @Transactional
    public Void execute(DeleteWishlistItemCommand command) {
        WishlistItem item = wishlistItemRepository.findById(command.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wishlist item not found: " + command.id()));
        item.setDeletedAt(LocalDateTime.now());
        return null;
    }
}
