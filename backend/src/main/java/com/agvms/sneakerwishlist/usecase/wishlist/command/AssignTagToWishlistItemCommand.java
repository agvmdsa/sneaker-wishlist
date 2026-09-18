package com.agvms.sneakerwishlist.usecase.wishlist.command;

import com.agvms.sneakerwishlist.dto.TagDto;
import com.agvms.sneakerwishlist.usecase.Command;

public record AssignTagToWishlistItemCommand(Long itemId, TagDto dto) implements Command {
}
