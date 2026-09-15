package com.agvms.sneakerwishlist.usecase.wishlist;

import com.agvms.sneakerwishlist.dto.PriceHistoryDto;
import com.agvms.sneakerwishlist.entity.Brand;
import com.agvms.sneakerwishlist.entity.PriceHistory;
import com.agvms.sneakerwishlist.entity.WishlistItem;
import com.agvms.sneakerwishlist.entity.WishlistStatus;
import com.agvms.sneakerwishlist.repository.PriceHistoryRepository;
import com.agvms.sneakerwishlist.repository.WishlistItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPriceHistoryUseCaseTest {

    @Mock
    private WishlistItemRepository wishlistItemRepository;

    @Mock
    private PriceHistoryRepository priceHistoryRepository;

    @InjectMocks
    private GetPriceHistoryUseCase useCase;

    @Test
    void throwsNotFoundWhenItemDoesNotExist() {
        when(wishlistItemRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(1L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("not found");
    }

    @Test
    void returnsHistoryOrderedChronologically() {
        WishlistItem item = new WishlistItem("ext-1", "Jordan 4", new Brand("Jordan"), "http://img",
                BigDecimal.valueOf(220), "10", WishlistStatus.WANT);
        when(wishlistItemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(priceHistoryRepository.findByWishlistItemIdOrderByCheckedAtAsc(1L))
                .thenReturn(List.of(
                        new PriceHistory(item, BigDecimal.valueOf(200)),
                        new PriceHistory(item, BigDecimal.valueOf(190))
                ));

        List<PriceHistoryDto> result = useCase.execute(1L);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).price()).isEqualByComparingTo("200");
        assertThat(result.get(1).price()).isEqualByComparingTo("190");
    }
}
