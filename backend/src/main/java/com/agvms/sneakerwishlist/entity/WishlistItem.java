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
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "wishlist_item")
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

    @Column(nullable = false)
    private String size;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WishlistStatus status;

    @Column(name = "price_paid", precision = 10, scale = 2)
    private BigDecimal pricePaid;

    @Column(columnDefinition = "text")
    private String notes;

    @Column(name = "price_drop_detected", nullable = false)
    private boolean priceDropDetected = false;

    @CreationTimestamp
    @Column(name = "added_at", nullable = false, updatable = false)
    private LocalDateTime addedAt;

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

    protected WishlistItem() {
    }

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

    public Long getId() {
        return id;
    }

    public String getExternalSneakerId() {
        return externalSneakerId;
    }

    public String getName() {
        return name;
    }

    public Brand getBrand() {
        return brand;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public WishlistStatus getStatus() {
        return status;
    }

    public void setStatus(WishlistStatus status) {
        this.status = status;
    }

    public BigDecimal getPricePaid() {
        return pricePaid;
    }

    public void setPricePaid(BigDecimal pricePaid) {
        this.pricePaid = pricePaid;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isPriceDropDetected() {
        return priceDropDetected;
    }

    public void setPriceDropDetected(boolean priceDropDetected) {
        this.priceDropDetected = priceDropDetected;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public List<PriceHistory> getPriceHistory() {
        return priceHistory;
    }
}
