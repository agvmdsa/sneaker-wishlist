package com.agvms.sneakerwishlist.dto;

import java.util.List;

public record UpcomingReleasesDto(
        List<SneakerSummaryDto> releasingSoon,
        List<SneakerSummaryDto> releasingThisMonth,
        List<SneakerSummaryDto> releasingLater
) {
}
