package com.agvms.sneakerwishlist.usecase.wishlist.command;

import com.agvms.sneakerwishlist.dto.WishlistItemCreateDto;
import com.agvms.sneakerwishlist.usecase.Command;

public record AddWishlistItemCommand(WishlistItemCreateDto dto) implements Command {
}
