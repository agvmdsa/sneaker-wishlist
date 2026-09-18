package com.agvms.sneakerwishlist.usecase.wishlist.command;

import com.agvms.sneakerwishlist.dto.StatusUpdateDto;
import com.agvms.sneakerwishlist.dto.WishlistItemDto;
import com.agvms.sneakerwishlist.entity.Brand;
import com.agvms.sneakerwishlist.entity.WishlistItem;
import com.agvms.sneakerwishlist.entity.WishlistStatus;
import com.agvms.sneakerwishlist.repository.WishlistItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateWishlistStatusUseCaseTest {

    @Mock
    private WishlistItemRepository wishlistItemRepository;

    @InjectMocks
    private UpdateWishlistStatusUseCase useCase;

    private WishlistItem itemWithStatus(WishlistStatus status) {
        return new WishlistItem("ext-1", "Jordan 4", new Brand("Jordan"), "http://img",
                BigDecimal.valueOf(220), "10", status);
    }

    @Test
    void throwsNotFoundWhenItemDoesNotExist() {
        when(wishlistItemRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(new UpdateWishlistStatusCommand(1L, new StatusUpdateDto(WishlistStatus.OWNED, BigDecimal.TEN))))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("not found");
    }

    @Test
    void isIdempotentWhenAlreadyAtTargetStatus() {
        WishlistItem item = itemWithStatus(WishlistStatus.WANT);
        when(wishlistItemRepository.findById(1L)).thenReturn(Optional.of(item));

        WishlistItemDto result = useCase.execute(new UpdateWishlistStatusCommand(1L, new StatusUpdateDto(WishlistStatus.WANT, null)));

        assertThat(result.status()).isEqualTo(WishlistStatus.WANT);
    }

    @Test
    void rejectsInvalidTransition() {
        WishlistItem item = itemWithStatus(WishlistStatus.WANT);
        when(wishlistItemRepository.findById(1L)).thenReturn(Optional.of(item));

        assertThatThrownBy(() -> useCase.execute(new UpdateWishlistStatusCommand(1L, new StatusUpdateDto(WishlistStatus.SOLD, null))))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Cannot transition");
    }

    @Test
    void requiresPricePaidWhenMovingToOwned() {
        WishlistItem item = itemWithStatus(WishlistStatus.WANT);
        when(wishlistItemRepository.findById(1L)).thenReturn(Optional.of(item));

        assertThatThrownBy(() -> useCase.execute(new UpdateWishlistStatusCommand(1L, new StatusUpdateDto(WishlistStatus.OWNED, null))))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("pricePaid");
    }

    @Test
    void appliesValidTransitionAndSetsPricePaid() {
        WishlistItem item = itemWithStatus(WishlistStatus.WANT);
        when(wishlistItemRepository.findById(1L)).thenReturn(Optional.of(item));

        WishlistItemDto result = useCase.execute(new UpdateWishlistStatusCommand(1L, new StatusUpdateDto(WishlistStatus.OWNED, BigDecimal.valueOf(180))));

        assertThat(result.status()).isEqualTo(WishlistStatus.OWNED);
        assertThat(result.pricePaid()).isEqualByComparingTo("180");
    }
}
