package com.agvms.sneakerwishlist.controller;

import com.agvms.sneakerwishlist.client.SneakerCatalogClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sneakers")
public class SneakerController {

    private final SneakerCatalogClient sneakerCatalogClient;

    public SneakerController(SneakerCatalogClient sneakerCatalogClient) {
        this.sneakerCatalogClient = sneakerCatalogClient;
    }

    @GetMapping("/search")
    public ResponseEntity<String> search(@RequestParam(required = false) String query,
                                          @RequestParam(required = false) String filters,
                                          @RequestParam(required = false) String sort,
                                          @RequestParam(required = false) Integer page,
                                          @RequestParam(required = false) Integer limit) {
        String body = sneakerCatalogClient.search(query, filters, sort, page, limit);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(body);
    }
}
