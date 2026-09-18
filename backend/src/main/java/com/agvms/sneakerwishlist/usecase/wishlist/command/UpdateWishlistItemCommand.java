package com.agvms.sneakerwishlist.usecase.wishlist.command;

import com.agvms.sneakerwishlist.dto.WishlistItemUpdateDto;
import com.agvms.sneakerwishlist.usecase.Command;

public record UpdateWishlistItemCommand(Long id, WishlistItemUpdateDto dto) implements Command {
}
