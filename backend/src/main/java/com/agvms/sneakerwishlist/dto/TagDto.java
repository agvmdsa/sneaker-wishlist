package com.agvms.sneakerwishlist.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record TagDto(
        @Schema(example = "grail") String name
) {
}
