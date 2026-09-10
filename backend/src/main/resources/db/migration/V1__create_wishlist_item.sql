CREATE TABLE wishlist_item (
    id                   BIGSERIAL PRIMARY KEY,
    external_sneaker_id  VARCHAR(64)    NOT NULL,
    name                 VARCHAR(255)   NOT NULL,
    image_url            VARCHAR(500),
    retail_price         NUMERIC(10, 2),
    size                 VARCHAR(16)    NOT NULL,
    status               VARCHAR(16)    NOT NULL,
    price_paid           NUMERIC(10, 2),
    notes                TEXT,
    added_at             TIMESTAMP      NOT NULL DEFAULT now(),
    deleted_at           TIMESTAMP
);

CREATE INDEX idx_wishlist_item_external_sneaker_id ON wishlist_item (external_sneaker_id);
CREATE INDEX idx_wishlist_item_status ON wishlist_item (status);
