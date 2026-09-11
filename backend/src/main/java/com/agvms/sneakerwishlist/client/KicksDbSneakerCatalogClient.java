package com.agvms.sneakerwishlist.client;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Component
public class KicksDbSneakerCatalogClient implements SneakerCatalogClient {

    private static final String SEARCH_PATH = "/v3/stockx/products";
    private static final String PRODUCT_BY_ID_PATH = "/v3/stockx/products/{id}";
    private static final String BRANDS_PATH = "/v3/utils/brands";

    private final RestTemplate restTemplate;

    public KicksDbSneakerCatalogClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String search(String query, String filters, String sort, Integer page, Integer limit) {
        String uri = UriComponentsBuilder.fromPath(SEARCH_PATH)
                .queryParamIfPresent("query", Optional.ofNullable(query))
                .queryParamIfPresent("filters", Optional.ofNullable(filters))
                .queryParamIfPresent("sort", Optional.ofNullable(sort))
                .queryParamIfPresent("page", Optional.ofNullable(page))
                .queryParamIfPresent("limit", Optional.ofNullable(limit))
                .toUriString();
        return restTemplate.getForObject(uri, String.class);
    }

    @Override
    public String getById(String id) {
        return restTemplate.getForObject(PRODUCT_BY_ID_PATH, String.class, id);
    }

    @Override
    @Cacheable("brands")
    public String getBrands(Integer page, Integer limit) {
        String uri = UriComponentsBuilder.fromPath(BRANDS_PATH)
                .queryParamIfPresent("page", Optional.ofNullable(page))
                .queryParamIfPresent("limit", Optional.ofNullable(limit))
                .toUriString();
        return restTemplate.getForObject(uri, String.class);
    }
}
