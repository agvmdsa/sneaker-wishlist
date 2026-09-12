package com.agvms.sneakerwishlist.usecase.wishlist;

import com.agvms.sneakerwishlist.client.SneakerCatalogClient;
import com.agvms.sneakerwishlist.dto.SneakerSummaryDto;
import com.agvms.sneakerwishlist.entity.PriceHistory;
import com.agvms.sneakerwishlist.entity.WishlistItem;
import com.agvms.sneakerwishlist.entity.WishlistStatus;
import com.agvms.sneakerwishlist.repository.PriceHistoryRepository;
import com.agvms.sneakerwishlist.repository.WishlistItemRepository;
import com.agvms.sneakerwishlist.usecase.sneaker.SneakerResponseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CheckWantedItemPricesUseCase {

    private static final Logger log = LoggerFactory.getLogger(CheckWantedItemPricesUseCase.class);

    private final WishlistItemRepository wishlistItemRepository;
    private final PriceHistoryRepository priceHistoryRepository;
    private final SneakerCatalogClient sneakerCatalogClient;
    private final SneakerResponseMapper sneakerResponseMapper;

    public CheckWantedItemPricesUseCase(WishlistItemRepository wishlistItemRepository,
                                         PriceHistoryRepository priceHistoryRepository,
                                         SneakerCatalogClient sneakerCatalogClient,
                                         SneakerResponseMapper sneakerResponseMapper) {
        this.wishlistItemRepository = wishlistItemRepository;
        this.priceHistoryRepository = priceHistoryRepository;
        this.sneakerCatalogClient = sneakerCatalogClient;
        this.sneakerResponseMapper = sneakerResponseMapper;
    }

    @Transactional
    public void execute() {
        Page<WishlistItem> wantedItems = wishlistItemRepository.findByStatusInAndDeletedAtIsNull(
                List.of(WishlistStatus.WANT), Pageable.unpaged());

        for (WishlistItem item : wantedItems) {
            try {
                String rawBody = sneakerCatalogClient.getById(item.getExternalSneakerId());
                SneakerSummaryDto summary = sneakerResponseMapper.toSummary(rawBody);
                BigDecimal currentPrice = summary.avgPrice();

                priceHistoryRepository.save(new PriceHistory(item, currentPrice));

                if (currentPrice != null && item.getRetailPrice() != null
                        && currentPrice.compareTo(item.getRetailPrice()) < 0) {
                    item.setPriceDropDetected(true);
                }
            } catch (Exception e) {
                log.warn("Price check failed for wishlist item {} (external id {}): {}",
                        item.getId(), item.getExternalSneakerId(), e.getMessage());
            }
        }
    }
}
