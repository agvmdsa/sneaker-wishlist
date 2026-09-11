package com.agvms.sneakerwishlist.service;

import com.agvms.sneakerwishlist.client.KicksDbProduct;
import com.agvms.sneakerwishlist.client.KicksDbProductResponse;
import com.agvms.sneakerwishlist.client.KicksDbSearchResponse;
import com.agvms.sneakerwishlist.client.SneakerCatalogClient;
import com.agvms.sneakerwishlist.dto.SneakerSummaryDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SneakerService {

    private static final String SNEAKER_PRODUCT_TYPE = "sneakers";

    private final SneakerCatalogClient sneakerCatalogClient;
    private final ObjectMapper objectMapper;

    public SneakerService(SneakerCatalogClient sneakerCatalogClient, ObjectMapper objectMapper) {
        this.sneakerCatalogClient = sneakerCatalogClient;
        this.objectMapper = objectMapper;
    }

    public List<SneakerSummaryDto> search(String query, String filters, String sort, Integer page, Integer limit) {
        String rawBody = sneakerCatalogClient.search(query, filters, sort, page, limit);
        KicksDbSearchResponse response = parse(rawBody, KicksDbSearchResponse.class);
        return response.data().stream()
                .filter(this::isSneaker)
                .map(this::toSummary)
                .toList();
    }

    public SneakerSummaryDto getById(String id) {
        String rawBody = sneakerCatalogClient.getById(id);
        KicksDbProductResponse response = parse(rawBody, KicksDbProductResponse.class);
        return toSummary(response.data());
    }

    private boolean isSneaker(KicksDbProduct product) {
        return SNEAKER_PRODUCT_TYPE.equals(product.productType());
    }

    private SneakerSummaryDto toSummary(KicksDbProduct product) {
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
