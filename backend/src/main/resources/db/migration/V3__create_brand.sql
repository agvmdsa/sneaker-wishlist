CREATE TABLE brand (
    id    BIGSERIAL PRIMARY KEY,
    name  VARCHAR(100) NOT NULL UNIQUE
);

ALTER TABLE wishlist_item ADD COLUMN brand_id BIGINT NOT NULL REFERENCES brand (id);

CREATE INDEX idx_wishlist_item_brand_id ON wishlist_item (brand_id);
