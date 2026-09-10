package com.agvms.sneakerwishlist.client;

public interface SneakerCatalogClient {

    String search(String query, String filters, String sort, Integer page, Integer limit);

    String getById(String id);

    String getBrands(Integer page, Integer limit);
}
