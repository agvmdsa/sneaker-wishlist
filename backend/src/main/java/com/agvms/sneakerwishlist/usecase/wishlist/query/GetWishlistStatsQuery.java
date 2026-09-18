package com.agvms.sneakerwishlist.usecase.wishlist.query;

import com.agvms.sneakerwishlist.dto.StatsDto;
import com.agvms.sneakerwishlist.usecase.Query;

public record GetWishlistStatsQuery() implements Query<StatsDto> {
}
