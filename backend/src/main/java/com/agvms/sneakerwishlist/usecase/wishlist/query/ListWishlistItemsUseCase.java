package com.agvms.sneakerwishlist.usecase.wishlist.query;

import com.agvms.sneakerwishlist.dto.WishlistItemDto;
import com.agvms.sneakerwishlist.entity.WishlistItem;
import com.agvms.sneakerwishlist.entity.WishlistStatus;
import com.agvms.sneakerwishlist.repository.WishlistItemRepository;
import com.agvms.sneakerwishlist.usecase.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListWishlistItemsUseCase implements QueryHandler<ListWishlistItemsQuery, Page<WishlistItemDto>> {

    private final WishlistItemRepository wishlistItemRepository;

    public ListWishlistItemsUseCase(WishlistItemRepository wishlistItemRepository) {
        this.wishlistItemRepository = wishlistItemRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WishlistItemDto> execute(ListWishlistItemsQuery query) {
        List<WishlistStatus> statuses = query.status() != null
                ? List.of(query.status())
                : List.of(WishlistStatus.WANT, WishlistStatus.OWNED);

        Page<WishlistItem> page = (query.tag() != null)
                ? wishlistItemRepository.findDistinctByStatusInAndDeletedAtIsNullAndTagsNameStartingWithIgnoreCase(statuses, query.tag(), query.pageable())
                : wishlistItemRepository.findByStatusInAndDeletedAtIsNull(statuses, query.pageable());

        return page.map(WishlistItemDto::from);
    }
}
