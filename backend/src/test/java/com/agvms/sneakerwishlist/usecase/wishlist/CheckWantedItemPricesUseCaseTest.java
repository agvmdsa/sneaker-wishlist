package com.agvms.sneakerwishlist.usecase.wishlist;

import com.agvms.sneakerwishlist.client.SneakerCatalogClient;
import com.agvms.sneakerwishlist.dto.SneakerSummaryDto;
import com.agvms.sneakerwishlist.entity.Brand;
import com.agvms.sneakerwishlist.entity.PriceHistory;
import com.agvms.sneakerwishlist.entity.WishlistItem;
import com.agvms.sneakerwishlist.entity.WishlistStatus;
import com.agvms.sneakerwishlist.repository.PriceHistoryRepository;
import com.agvms.sneakerwishlist.repository.WishlistItemRepository;
import com.agvms.sneakerwishlist.usecase.sneaker.SneakerResponseMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckWantedItemPricesUseCaseTest {

    @Mock
    private WishlistItemRepository wishlistItemRepository;

    @Mock
    private PriceHistoryRepository priceHistoryRepository;

    @Mock
    private SneakerCatalogClient sneakerCatalogClient;

    @Mock
    private SneakerResponseMapper sneakerResponseMapper;

    @InjectMocks
    private CheckWantedItemPricesUseCase useCase;

    private WishlistItem itemWithRetailPrice(String externalId, BigDecimal retailPrice) {
        return new WishlistItem(externalId, "Jordan 4", new Brand("Jordan"), "http://img",
                retailPrice, "10", WishlistStatus.WANT);
    }

    @Test
    void isolatesFailureOfOneItemFromTheRest() {
        WishlistItem failingItem = itemWithRetailPrice("ext-fail", BigDecimal.valueOf(220));
        WishlistItem healthyItem = itemWithRetailPrice("ext-ok", BigDecimal.valueOf(220));
        when(wishlistItemRepository.findByStatusInAndDeletedAtIsNull(eq(List.of(WishlistStatus.WANT)), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(failingItem, healthyItem)));

        when(sneakerCatalogClient.getById("ext-fail")).thenThrow(new RuntimeException("external API down"));
        when(sneakerCatalogClient.getById("ext-ok")).thenReturn("raw-json");
        when(sneakerResponseMapper.toSummary("raw-json"))
                .thenReturn(new SneakerSummaryDto("ext-ok", "Jordan 4", "Jordan", "http://img", BigDecimal.valueOf(190), "sneakers"));

        assertThatCode(() -> useCase.execute()).doesNotThrowAnyException();

        verify(priceHistoryRepository).save(argThat((PriceHistory ph) -> ph.getPrice().compareTo(BigDecimal.valueOf(190)) == 0));
        assertThat(healthyItem.isPriceDropDetected()).isTrue();
        assertThat(failingItem.isPriceDropDetected()).isFalse();
    }
}
