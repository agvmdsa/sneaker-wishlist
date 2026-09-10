package com.agvms.sneakerwishlist.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Component
public class KicksDbSneakerCatalogClient implements SneakerCatalogClient {

    private final RestTemplate restTemplate;

    public KicksDbSneakerCatalogClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String search(String query, String filters, String sort, Integer page, Integer limit) {
        String uri = UriComponentsBuilder.fromPath("/v3/stockx/products")
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
        return restTemplate.getForObject("/v3/stockx/products/{id}", String.class, id);
    }

    @Override
    public String getBrands(Integer page, Integer limit) {
        String uri = UriComponentsBuilder.fromPath("/v3/utils/brands")
                .queryParamIfPresent("page", Optional.ofNullable(page))
                .queryParamIfPresent("limit", Optional.ofNullable(limit))
                .toUriString();
        return restTemplate.getForObject(uri, String.class);
    }
}
