package com.agvms.sneakerwishlist.usecase.wishlist;

import com.agvms.sneakerwishlist.dto.StatusUpdateDto;
import com.agvms.sneakerwishlist.dto.WishlistItemDto;
import com.agvms.sneakerwishlist.entity.WishlistItem;
import com.agvms.sneakerwishlist.entity.WishlistStatus;
import com.agvms.sneakerwishlist.repository.WishlistItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UpdateWishlistStatusUseCase {

    private final WishlistItemRepository wishlistItemRepository;

    public UpdateWishlistStatusUseCase(WishlistItemRepository wishlistItemRepository) {
        this.wishlistItemRepository = wishlistItemRepository;
    }

    @Transactional
    public WishlistItemDto execute(Long id, StatusUpdateDto dto) {
        WishlistItem item = wishlistItemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wishlist item not found: " + id));

        WishlistStatus current = item.getStatus();
        WishlistStatus target = dto.status();

        if (current == target) {
            return WishlistItemDto.from(item);
        }

        if (!current.canTransitionTo(target)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Cannot transition from " + current + " to " + target);
        }

        if (target == WishlistStatus.OWNED && dto.pricePaid() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "pricePaid is required when moving to OWNED");
        }

        item.setStatus(target);
        if (target == WishlistStatus.OWNED) {
            item.setPricePaid(dto.pricePaid());
        }
        return WishlistItemDto.from(item);
    }
}
