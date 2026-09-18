package com.agvms.sneakerwishlist.usecase.wishlist.query;

import com.agvms.sneakerwishlist.entity.WishlistItem;
import com.agvms.sneakerwishlist.repository.WishlistItemRepository;
import com.agvms.sneakerwishlist.usecase.QueryHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CheckAlreadyInCollectionUseCase implements QueryHandler<CheckAlreadyInCollectionQuery, Set<String>> {

    private final WishlistItemRepository wishlistItemRepository;

    public CheckAlreadyInCollectionUseCase(WishlistItemRepository wishlistItemRepository) {
        this.wishlistItemRepository = wishlistItemRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<String> execute(CheckAlreadyInCollectionQuery query) {
        return wishlistItemRepository.findByExternalSneakerIdInAndDeletedAtIsNull(query.externalSneakerIds())
                .stream()
                .map(WishlistItem::getExternalSneakerId)
                .collect(Collectors.toSet());
    }
}
