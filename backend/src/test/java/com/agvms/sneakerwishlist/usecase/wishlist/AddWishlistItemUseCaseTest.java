package com.agvms.sneakerwishlist.usecase.wishlist;

import com.agvms.sneakerwishlist.dto.WishlistItemCreateDto;
import com.agvms.sneakerwishlist.dto.WishlistItemDto;
import com.agvms.sneakerwishlist.entity.Brand;
import com.agvms.sneakerwishlist.entity.WishlistItem;
import com.agvms.sneakerwishlist.entity.WishlistStatus;
import com.agvms.sneakerwishlist.repository.BrandRepository;
import com.agvms.sneakerwishlist.repository.WishlistItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddWishlistItemUseCaseTest {

    @Mock
    private WishlistItemRepository wishlistItemRepository;

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private AddWishlistItemUseCase useCase;

    private final WishlistItemCreateDto dto = new WishlistItemCreateDto(
            "ext-1", "Jordan 4", "Jordan", "http://img", BigDecimal.valueOf(220), "10");

    @Test
    void returnsExistingItemInsteadOfCreatingDuplicate() {
        Brand brand = new Brand("Jordan");
        WishlistItem existing = new WishlistItem("ext-1", "Jordan 4", brand, "http://img",
                BigDecimal.valueOf(220), "10", WishlistStatus.WANT);
        when(wishlistItemRepository.findByExternalSneakerIdAndSizeAndDeletedAtIsNull("ext-1", "10"))
                .thenReturn(Optional.of(existing));

        WishlistItemDto result = useCase.execute(dto);

        assertThat(result.externalSneakerId()).isEqualTo("ext-1");
        verify(wishlistItemRepository, never()).save(any());
        verify(brandRepository, never()).save(any());
    }

    @Test
    void createsNewItemAndReusesExistingBrand() {
        when(wishlistItemRepository.findByExternalSneakerIdAndSizeAndDeletedAtIsNull("ext-1", "10"))
                .thenReturn(Optional.empty());
        Brand brand = new Brand("Jordan");
        when(brandRepository.findByNameIgnoreCase("Jordan")).thenReturn(Optional.of(brand));
        when(wishlistItemRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        WishlistItemDto result = useCase.execute(dto);

        assertThat(result.status()).isEqualTo(WishlistStatus.WANT);
        assertThat(result.brand()).isEqualTo("Jordan");
        verify(brandRepository, never()).save(any());
    }

    @Test
    void createsNewBrandWhenItDoesNotExistYet() {
        when(wishlistItemRepository.findByExternalSneakerIdAndSizeAndDeletedAtIsNull("ext-1", "10"))
                .thenReturn(Optional.empty());
        when(brandRepository.findByNameIgnoreCase("Jordan")).thenReturn(Optional.empty());
        when(brandRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(wishlistItemRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        useCase.execute(dto);

        verify(brandRepository).save(any());
    }
}
