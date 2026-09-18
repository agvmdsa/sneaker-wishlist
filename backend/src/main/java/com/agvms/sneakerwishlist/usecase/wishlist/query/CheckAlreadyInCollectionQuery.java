package com.agvms.sneakerwishlist.usecase.wishlist.query;

import com.agvms.sneakerwishlist.usecase.Query;

import java.util.List;
import java.util.Set;

public record CheckAlreadyInCollectionQuery(List<String> externalSneakerIds) implements Query<Set<String>> {
}
