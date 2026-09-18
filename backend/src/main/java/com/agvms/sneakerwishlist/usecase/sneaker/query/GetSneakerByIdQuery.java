package com.agvms.sneakerwishlist.usecase.sneaker.query;

import com.agvms.sneakerwishlist.dto.SneakerSummaryDto;
import com.agvms.sneakerwishlist.usecase.Query;

public record GetSneakerByIdQuery(String id) implements Query<SneakerSummaryDto> {
}
