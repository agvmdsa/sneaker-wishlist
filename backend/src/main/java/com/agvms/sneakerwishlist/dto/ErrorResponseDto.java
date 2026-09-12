package com.agvms.sneakerwishlist.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponseDto(
        LocalDateTime timestamp,
        int status,
        String message,
        List<String> fieldErrors
) {
}
