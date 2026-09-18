package com.agvms.sneakerwishlist.usecase.wishlist.query;

import com.agvms.sneakerwishlist.dto.WishlistItemDto;
import com.agvms.sneakerwishlist.entity.WishlistItem;
import com.agvms.sneakerwishlist.repository.WishlistItemRepository;
import com.agvms.sneakerwishlist.usecase.QueryHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class GetWishlistItemUseCase implements QueryHandler<GetWishlistItemQuery, WishlistItemDto> {

    private final WishlistItemRepository wishlistItemRepository;

    public GetWishlistItemUseCase(WishlistItemRepository wishlistItemRepository) {
        this.wishlistItemRepository = wishlistItemRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public WishlistItemDto execute(GetWishlistItemQuery query) {
        WishlistItem item = wishlistItemRepository.findById(query.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wishlist item not found: " + query.id()));

        return WishlistItemDto.from(item);
    }
}
