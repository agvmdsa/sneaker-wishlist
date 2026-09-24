package com.agvms.sneakerwishlist.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KicksDbProduct(
        String id,
        String title,
        String brand,
        String image,
        BigDecimal avgPrice,
        String productType,
        Integer weeklyOrders
) {
}
