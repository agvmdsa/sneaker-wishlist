package com.agvms.sneakerwishlist.usecase.wishlist.query;

import com.agvms.sneakerwishlist.dto.PriceHistoryDto;
import com.agvms.sneakerwishlist.usecase.Query;

import java.util.List;

public record GetPriceHistoryQuery(Long wishlistItemId) implements Query<List<PriceHistoryDto>> {
}
