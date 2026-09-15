package com.agvms.sneakerwishlist.usecase.wishlist;

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
class DeleteWishlistItemUseCaseTest {

    @Mock
    private WishlistItemRepository wishlistItemRepository;

    @InjectMocks
    private DeleteWishlistItemUseCase useCase;

    @Test
    void throwsNotFoundWhenItemDoesNotExist() {
        when(wishlistItemRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(1L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("not found");
    }

    @Test
    void marksItemAsDeletedWithoutRemovingTheRow() {
        WishlistItem item = new WishlistItem("ext-1", "Jordan 4", new Brand("Jordan"), "http://img",
                BigDecimal.valueOf(220), "10", WishlistStatus.WANT);
        when(wishlistItemRepository.findById(1L)).thenReturn(Optional.of(item));

        useCase.execute(1L);

        assertThat(item.getDeletedAt()).isNotNull();
    }
}
