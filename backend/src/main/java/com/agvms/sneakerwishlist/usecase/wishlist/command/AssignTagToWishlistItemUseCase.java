package com.agvms.sneakerwishlist.usecase.wishlist.command;

import com.agvms.sneakerwishlist.dto.WishlistItemDto;
import com.agvms.sneakerwishlist.entity.Tag;
import com.agvms.sneakerwishlist.entity.WishlistItem;
import com.agvms.sneakerwishlist.repository.TagRepository;
import com.agvms.sneakerwishlist.repository.WishlistItemRepository;
import com.agvms.sneakerwishlist.usecase.CommandHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AssignTagToWishlistItemUseCase implements CommandHandler<AssignTagToWishlistItemCommand, WishlistItemDto> {

    private final WishlistItemRepository wishlistItemRepository;
    private final TagRepository tagRepository;

    public AssignTagToWishlistItemUseCase(WishlistItemRepository wishlistItemRepository, TagRepository tagRepository) {
        this.wishlistItemRepository = wishlistItemRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    @Transactional
    public WishlistItemDto execute(AssignTagToWishlistItemCommand command) {
        WishlistItem item = wishlistItemRepository.findById(command.itemId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wishlist item not found: " + command.itemId()));

        Tag tag = tagRepository.findByNameIgnoreCase(command.dto().name())
                .orElseGet(() -> tagRepository.save(new Tag(command.dto().name())));

        item.getTags().add(tag);
        return WishlistItemDto.from(item);
    }
}
