package com.agvms.sneakerwishlist.usecase.wishlist;

import com.agvms.sneakerwishlist.entity.WishlistItem;
import com.agvms.sneakerwishlist.repository.WishlistItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CheckAlreadyInCollectionUseCase {

    private final WishlistItemRepository wishlistItemRepository;

    public CheckAlreadyInCollectionUseCase(WishlistItemRepository wishlistItemRepository) {
        this.wishlistItemRepository = wishlistItemRepository;
    }

    @Transactional(readOnly = true)
    public Set<String> execute(List<String> externalSneakerIds) {
        return wishlistItemRepository.findByExternalSneakerIdInAndDeletedAtIsNull(externalSneakerIds)
                .stream()
                .map(WishlistItem::getExternalSneakerId)
                .collect(Collectors.toSet());
    }
}
