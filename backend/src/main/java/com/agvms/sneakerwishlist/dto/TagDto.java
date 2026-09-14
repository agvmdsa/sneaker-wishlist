package com.agvms.sneakerwishlist.dto;

import com.agvms.sneakerwishlist.entity.Tag;
import io.swagger.v3.oas.annotations.media.Schema;

public record TagDto(
        @Schema(example = "3") Long id,
        @Schema(example = "grail") String name
) {

    public static TagDto from(Tag tag) {
        return new TagDto(tag.getId(), tag.getName());
    }
}
