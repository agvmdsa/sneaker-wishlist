package com.agvms.sneakerwishlist.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "wishlist_item")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WishlistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_sneaker_id", nullable = false)
    private String externalSneakerId;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "retail_price", precision = 10, scale = 2)
    private BigDecimal retailPrice;

    @Setter
    @Column(nullable = false)
    private String size;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WishlistStatus status;

    @Setter
    @Column(name = "price_paid", precision = 10, scale = 2)
    private BigDecimal pricePaid;

    @Setter
    @Column(columnDefinition = "text")
    private String notes;

    @Setter
    @Column(name = "price_drop_detected", nullable = false)
    private boolean priceDropDetected = false;

    @CreationTimestamp
    @Column(name = "added_at", nullable = false, updatable = false)
    private LocalDateTime addedAt;

    @Setter
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToMany
    @JoinTable(
            name = "wishlist_item_tag",
            joinColumns = @JoinColumn(name = "wishlist_item_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "wishlistItem", cascade = CascadeType.PERSIST)
    private List<PriceHistory> priceHistory = new ArrayList<>();

    public WishlistItem(String externalSneakerId, String name, Brand brand, String imageUrl,
                         BigDecimal retailPrice, String size, WishlistStatus status) {
        this.externalSneakerId = externalSneakerId;
        this.name = name;
        this.brand = brand;
        this.imageUrl = imageUrl;
        this.retailPrice = retailPrice;
        this.size = size;
        this.status = status;
    }
}
