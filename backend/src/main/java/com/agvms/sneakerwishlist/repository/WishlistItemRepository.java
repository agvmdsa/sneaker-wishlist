package com.agvms.sneakerwishlist.repository;

import com.agvms.sneakerwishlist.entity.WishlistItem;
import com.agvms.sneakerwishlist.entity.WishlistStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {

    List<WishlistItem> findByExternalSneakerIdInAndDeletedAtIsNull(Collection<String> externalSneakerIds);

    Optional<WishlistItem> findByExternalSneakerIdAndSizeAndDeletedAtIsNull(String externalSneakerId, String size);

    Page<WishlistItem> findByStatusInAndDeletedAtIsNull(Collection<WishlistStatus> statuses, Pageable pageable);

    Page<WishlistItem> findByStatusInAndDeletedAtIsNullAndTagsNameIgnoreCase(
            Collection<WishlistStatus> statuses, String tagName, Pageable pageable);

    long countByDeletedAtIsNull();

    @Query("SELECT COALESCE(SUM(w.pricePaid), 0) FROM WishlistItem w WHERE w.status IN :statuses AND w.deletedAt IS NULL")
    BigDecimal sumPricePaidByStatusIn(@Param("statuses") Collection<WishlistStatus> statuses);

    @Query("SELECT COALESCE(SUM(w.retailPrice), 0) FROM WishlistItem w WHERE w.status = :status AND w.deletedAt IS NULL")
    BigDecimal sumRetailPriceByStatus(@Param("status") WishlistStatus status);

    @Query("SELECT w.brand.name FROM WishlistItem w WHERE w.deletedAt IS NULL GROUP BY w.brand.name ORDER BY COUNT(w) DESC")
    List<String> findMostFrequentBrandNames(Pageable pageable);
}
