package com.agvms.sneakerwishlist.usecase.wishlist.command;

import com.agvms.sneakerwishlist.usecase.Command;

public record DeleteWishlistItemCommand(Long id) implements Command {
}
