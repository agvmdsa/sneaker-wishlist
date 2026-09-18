package com.agvms.sneakerwishlist.usecase.wishlist.query;

import com.agvms.sneakerwishlist.dto.WishlistItemDto;
import com.agvms.sneakerwishlist.usecase.Query;

public record GetWishlistItemQuery(Long id) implements Query<WishlistItemDto> {
}
