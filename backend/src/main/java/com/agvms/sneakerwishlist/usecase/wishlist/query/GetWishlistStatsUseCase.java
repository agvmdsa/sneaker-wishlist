package com.agvms.sneakerwishlist.usecase.wishlist.query;

import com.agvms.sneakerwishlist.dto.StatsDto;
import com.agvms.sneakerwishlist.entity.WishlistStatus;
import com.agvms.sneakerwishlist.repository.WishlistItemRepository;
import com.agvms.sneakerwishlist.usecase.QueryHandler;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetWishlistStatsUseCase implements QueryHandler<GetWishlistStatsQuery, StatsDto> {

    private final WishlistItemRepository wishlistItemRepository;

    public GetWishlistStatsUseCase(WishlistItemRepository wishlistItemRepository) {
        this.wishlistItemRepository = wishlistItemRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public StatsDto execute(GetWishlistStatsQuery query) {
        long totalActiveItems = wishlistItemRepository.countByDeletedAtIsNull();
        var totalSpent = wishlistItemRepository.sumPricePaidByStatusIn(
                List.of(WishlistStatus.OWNED, WishlistStatus.SOLD, WishlistStatus.DONATED));
        var estimatedWishlistValue = wishlistItemRepository.sumRetailPriceByStatus(WishlistStatus.WANT);
        List<String> mostFrequentBrands = wishlistItemRepository.findMostFrequentBrandNames(PageRequest.of(0, 1));
        String mostFrequentBrand = mostFrequentBrands.isEmpty() ? null : mostFrequentBrands.get(0);

        return new StatsDto(totalActiveItems, totalSpent, estimatedWishlistValue, mostFrequentBrand);
    }
}
