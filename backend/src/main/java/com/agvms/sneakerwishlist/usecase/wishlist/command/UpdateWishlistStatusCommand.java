package com.agvms.sneakerwishlist.usecase.wishlist.command;

import com.agvms.sneakerwishlist.dto.StatusUpdateDto;
import com.agvms.sneakerwishlist.usecase.Command;

public record UpdateWishlistStatusCommand(Long id, StatusUpdateDto dto) implements Command {
}
