package com.agvms.sneakerwishlist.usecase.wishlist;

import com.agvms.sneakerwishlist.dto.WishlistItemDto;
import com.agvms.sneakerwishlist.entity.WishlistItem;
import com.agvms.sneakerwishlist.entity.WishlistStatus;
import com.agvms.sneakerwishlist.repository.WishlistItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListWishlistItemsUseCase {

    private final WishlistItemRepository wishlistItemRepository;

    public ListWishlistItemsUseCase(WishlistItemRepository wishlistItemRepository) {
        this.wishlistItemRepository = wishlistItemRepository;
    }

    @Transactional(readOnly = true)
    public Page<WishlistItemDto> execute(WishlistStatus status, String tag, Pageable pageable) {
        List<WishlistStatus> statuses = status != null
                ? List.of(status)
                : List.of(WishlistStatus.WANT, WishlistStatus.OWNED);

        Page<WishlistItem> page = (tag != null)
                ? wishlistItemRepository.findDistinctByStatusInAndDeletedAtIsNullAndTagsNameStartingWithIgnoreCase(statuses, tag, pageable)
                : wishlistItemRepository.findByStatusInAndDeletedAtIsNull(statuses, pageable);

        return page.map(WishlistItemDto::from);
    }
}
