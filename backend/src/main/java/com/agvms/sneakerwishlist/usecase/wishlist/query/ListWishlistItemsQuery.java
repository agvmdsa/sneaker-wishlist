package com.agvms.sneakerwishlist.usecase.wishlist.query;

import com.agvms.sneakerwishlist.entity.WishlistStatus;
import com.agvms.sneakerwishlist.usecase.Query;
import com.agvms.sneakerwishlist.dto.WishlistItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public record ListWishlistItemsQuery(WishlistStatus status, String tag, Pageable pageable)
        implements Query<Page<WishlistItemDto>> {
}
