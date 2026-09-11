package com.agvms.sneakerwishlist.usecase.wishlist;

import com.agvms.sneakerwishlist.dto.TagDto;
import com.agvms.sneakerwishlist.dto.WishlistItemDto;
import com.agvms.sneakerwishlist.entity.Tag;
import com.agvms.sneakerwishlist.entity.WishlistItem;
import com.agvms.sneakerwishlist.repository.TagRepository;
import com.agvms.sneakerwishlist.repository.WishlistItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AssignTagToWishlistItemUseCase {

    private final WishlistItemRepository wishlistItemRepository;
    private final TagRepository tagRepository;

    public AssignTagToWishlistItemUseCase(WishlistItemRepository wishlistItemRepository, TagRepository tagRepository) {
        this.wishlistItemRepository = wishlistItemRepository;
        this.tagRepository = tagRepository;
    }

    @Transactional
    public WishlistItemDto execute(Long itemId, TagDto dto) {
        WishlistItem item = wishlistItemRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wishlist item not found: " + itemId));

        Tag tag = tagRepository.findByNameIgnoreCase(dto.name())
                .orElseGet(() -> tagRepository.save(new Tag(dto.name())));

        item.getTags().add(tag);
        return WishlistItemDto.from(item);
    }
}
