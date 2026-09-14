package com.agvms.sneakerwishlist.usecase.wishlist;

import com.agvms.sneakerwishlist.dto.PriceHistoryDto;
import com.agvms.sneakerwishlist.repository.PriceHistoryRepository;
import com.agvms.sneakerwishlist.repository.WishlistItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class GetPriceHistoryUseCase {

    private final WishlistItemRepository wishlistItemRepository;
    private final PriceHistoryRepository priceHistoryRepository;

    public GetPriceHistoryUseCase(WishlistItemRepository wishlistItemRepository,
                                   PriceHistoryRepository priceHistoryRepository) {
        this.wishlistItemRepository = wishlistItemRepository;
        this.priceHistoryRepository = priceHistoryRepository;
    }

    @Transactional(readOnly = true)
    public List<PriceHistoryDto> execute(Long wishlistItemId) {
        wishlistItemRepository.findById(wishlistItemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wishlist item not found: " + wishlistItemId));

        return priceHistoryRepository.findByWishlistItemIdOrderByCheckedAtAsc(wishlistItemId).stream()
                .map(PriceHistoryDto::from)
                .toList();
    }
}
