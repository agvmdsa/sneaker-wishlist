CREATE TABLE tag (
    id    BIGSERIAL PRIMARY KEY,
    name  VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE wishlist_item_tag (
    wishlist_item_id  BIGINT NOT NULL REFERENCES wishlist_item (id),
    tag_id            BIGINT NOT NULL REFERENCES tag (id),
    PRIMARY KEY (wishlist_item_id, tag_id)
);
