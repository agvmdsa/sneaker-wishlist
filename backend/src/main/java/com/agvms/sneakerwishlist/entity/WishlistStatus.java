package com.agvms.sneakerwishlist.entity;

public enum WishlistStatus {
    WANT,
    OWNED,
    SOLD,
    DONATED;

    public boolean canTransitionTo(WishlistStatus target) {
        return switch (this) {
            case WANT -> target == OWNED;
            case OWNED -> target == SOLD || target == DONATED;
            case SOLD, DONATED -> false;
        };
    }
}
