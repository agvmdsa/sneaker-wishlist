CREATE TABLE price_history (
    id                  BIGSERIAL PRIMARY KEY,
    wishlist_item_id    BIGINT NOT NULL REFERENCES wishlist_item (id),
    price               NUMERIC(10, 2) NOT NULL,
    checked_at          TIMESTAMP NOT NULL DEFAULT now()
);

CREATE INDEX idx_price_history_wishlist_item_id ON price_history (wishlist_item_id);

ALTER TABLE wishlist_item ADD COLUMN price_drop_detected BOOLEAN NOT NULL DEFAULT false;
