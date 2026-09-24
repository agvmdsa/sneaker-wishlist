package com.agvms.sneakerwishlist.client;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
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
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(SEARCH_PATH);
        Map<String, Object> uriVariables = new HashMap<>();
        if (query != null) {
            builder.queryParam("query", "{query}");
            uriVariables.put("query", query);
        }
        if (filters != null) {
            builder.queryParam("filters", "{filters}");
            uriVariables.put("filters", filters);
        }
        if (sort != null) {
            builder.queryParam("sort", "{sort}");
            uriVariables.put("sort", sort);
        }
        if (page != null) {
            builder.queryParam("page", "{page}");
            uriVariables.put("page", page);
        }
        if (limit != null) {
            builder.queryParam("limit", "{limit}");
            uriVariables.put("limit", limit);
        }
        String uriTemplate = builder.build().toUriString();
        return restTemplate.getForObject(uriTemplate, String.class, uriVariables);
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
