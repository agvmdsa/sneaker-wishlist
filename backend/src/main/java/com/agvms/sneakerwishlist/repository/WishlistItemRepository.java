package com.agvms.sneakerwishlist.repository;

import com.agvms.sneakerwishlist.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {

    List<WishlistItem> findByExternalSneakerIdInAndDeletedAtIsNull(Collection<String> externalSneakerIds);
}
