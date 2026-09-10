package com.agvms.sneakerwishlist.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "price_history")
public class PriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wishlist_item_id", nullable = false)
    private WishlistItem wishlistItem;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @CreationTimestamp
    @Column(name = "checked_at", nullable = false, updatable = false)
    private LocalDateTime checkedAt;

    protected PriceHistory() {
    }

    public PriceHistory(WishlistItem wishlistItem, BigDecimal price) {
        this.wishlistItem = wishlistItem;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public WishlistItem getWishlistItem() {
        return wishlistItem;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDateTime getCheckedAt() {
        return checkedAt;
    }
}
