package com.agvms.sneakerwishlist.repository;

import com.agvms.sneakerwishlist.entity.WishlistItem;
import com.agvms.sneakerwishlist.entity.WishlistStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {

    List<WishlistItem> findByExternalSneakerIdInAndDeletedAtIsNull(Collection<String> externalSneakerIds);

    Page<WishlistItem> findByStatusInAndDeletedAtIsNull(Collection<WishlistStatus> statuses, Pageable pageable);

    Page<WishlistItem> findByStatusInAndDeletedAtIsNullAndTagsNameIgnoreCase(
            Collection<WishlistStatus> statuses, String tagName, Pageable pageable);
}
