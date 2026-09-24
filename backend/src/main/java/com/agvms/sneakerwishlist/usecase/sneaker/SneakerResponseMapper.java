package com.agvms.sneakerwishlist.usecase.sneaker;

import com.agvms.sneakerwishlist.client.KicksDbProduct;
import com.agvms.sneakerwishlist.client.KicksDbProductResponse;
import com.agvms.sneakerwishlist.client.KicksDbSearchResponse;
import com.agvms.sneakerwishlist.dto.SneakerSummaryDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
public class SneakerResponseMapper {

    private static final String SNEAKER_PRODUCT_TYPE = "sneakers";

    private final ObjectMapper objectMapper;

    public SneakerResponseMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<SneakerSummaryDto> toSummaryList(String rawSearchResponse) {
        return toSneakerProducts(rawSearchResponse).stream()
                .map(this::toSummary)
                .toList();
    }

    public List<KicksDbProduct> toSneakerProducts(String rawSearchResponse) {
        KicksDbSearchResponse response = parse(rawSearchResponse, KicksDbSearchResponse.class);
        return response.data().stream()
                .filter(this::isSneaker)
                .toList();
    }

    public SneakerSummaryDto toSummary(String rawProductResponse) {
        KicksDbProductResponse response = parse(rawProductResponse, KicksDbProductResponse.class);
        return toSummary(response.data());
    }

    private boolean isSneaker(KicksDbProduct product) {
        return SNEAKER_PRODUCT_TYPE.equals(product.productType());
    }

    public SneakerSummaryDto toSummary(KicksDbProduct product) {
        return new SneakerSummaryDto(
                product.id(),
                product.title(),
                product.brand(),
                product.image(),
                product.avgPrice(),
                product.productType()
        );
    }

    private <T> T parse(String json, Class<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Invalid response from external API", e);
        }
    }
}
