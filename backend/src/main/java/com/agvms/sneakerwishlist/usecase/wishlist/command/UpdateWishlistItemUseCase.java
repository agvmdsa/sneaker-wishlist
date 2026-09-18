package com.agvms.sneakerwishlist.usecase.wishlist.command;

import com.agvms.sneakerwishlist.dto.WishlistItemDto;
import com.agvms.sneakerwishlist.dto.WishlistItemUpdateDto;
import com.agvms.sneakerwishlist.entity.WishlistItem;
import com.agvms.sneakerwishlist.repository.WishlistItemRepository;
import com.agvms.sneakerwishlist.usecase.CommandHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UpdateWishlistItemUseCase implements CommandHandler<UpdateWishlistItemCommand, WishlistItemDto> {

    private final WishlistItemRepository wishlistItemRepository;

    public UpdateWishlistItemUseCase(WishlistItemRepository wishlistItemRepository) {
        this.wishlistItemRepository = wishlistItemRepository;
    }

    @Override
    @Transactional
    public WishlistItemDto execute(UpdateWishlistItemCommand command) {
        WishlistItem item = wishlistItemRepository.findById(command.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wishlist item not found: " + command.id()));

        WishlistItemUpdateDto dto = command.dto();
        if (dto.size() != null) {
            item.setSize(dto.size());
        }
        if (dto.notes() != null) {
            item.setNotes(dto.notes());
        }
        return WishlistItemDto.from(item);
    }
}
