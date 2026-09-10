package com.agvms.sneakerwishlist.repository;

import com.agvms.sneakerwishlist.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
}
