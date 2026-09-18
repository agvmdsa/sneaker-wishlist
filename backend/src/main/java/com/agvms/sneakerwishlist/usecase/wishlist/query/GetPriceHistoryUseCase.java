package com.agvms.sneakerwishlist.usecase.wishlist.query;

import com.agvms.sneakerwishlist.dto.PriceHistoryDto;
import com.agvms.sneakerwishlist.repository.PriceHistoryRepository;
import com.agvms.sneakerwishlist.repository.WishlistItemRepository;
import com.agvms.sneakerwishlist.usecase.QueryHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class GetPriceHistoryUseCase implements QueryHandler<GetPriceHistoryQuery, List<PriceHistoryDto>> {

    private final WishlistItemRepository wishlistItemRepository;
    private final PriceHistoryRepository priceHistoryRepository;

    public GetPriceHistoryUseCase(WishlistItemRepository wishlistItemRepository,
                                   PriceHistoryRepository priceHistoryRepository) {
        this.wishlistItemRepository = wishlistItemRepository;
        this.priceHistoryRepository = priceHistoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PriceHistoryDto> execute(GetPriceHistoryQuery query) {
        wishlistItemRepository.findById(query.wishlistItemId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wishlist item not found: " + query.wishlistItemId()));

        return priceHistoryRepository.findByWishlistItemIdOrderByCheckedAtAsc(query.wishlistItemId()).stream()
                .map(PriceHistoryDto::from)
                .toList();
    }
}
