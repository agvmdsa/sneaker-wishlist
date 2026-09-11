CREATE UNIQUE INDEX uq_wishlist_item_external_id_size_active
    ON wishlist_item (external_sneaker_id, size)
    WHERE deleted_at IS NULL;
