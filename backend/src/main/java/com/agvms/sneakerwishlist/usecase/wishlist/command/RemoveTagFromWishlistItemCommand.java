package com.agvms.sneakerwishlist.usecase.wishlist.command;

import com.agvms.sneakerwishlist.usecase.Command;

public record RemoveTagFromWishlistItemCommand(Long itemId, Long tagId) implements Command {
}
