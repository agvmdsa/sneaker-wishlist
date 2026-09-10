package com.agvms.sneakerwishlist.repository;

import com.agvms.sneakerwishlist.entity.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Long> {
}
