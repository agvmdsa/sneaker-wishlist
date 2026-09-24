package com.agvms.sneakerwishlist.usecase.sneaker.query;

import com.agvms.sneakerwishlist.dto.UpcomingReleasesDto;
import com.agvms.sneakerwishlist.usecase.Query;

public record GetUpcomingReleasesQuery() implements Query<UpcomingReleasesDto> {
}
