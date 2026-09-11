package com.agvms.sneakerwishlist.controller;

import com.agvms.sneakerwishlist.client.SneakerCatalogClient;
import com.agvms.sneakerwishlist.dto.SneakerSummaryDto;
import com.agvms.sneakerwishlist.service.SneakerService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sneakers")
public class SneakerController {

    private final SneakerCatalogClient sneakerCatalogClient;
    private final SneakerService sneakerService;

    public SneakerController(SneakerCatalogClient sneakerCatalogClient, SneakerService sneakerService) {
        this.sneakerCatalogClient = sneakerCatalogClient;
        this.sneakerService = sneakerService;
    }

    @GetMapping("/search")
    public List<SneakerSummaryDto> search(@RequestParam(required = false) String query,
                                           @RequestParam(required = false) String filters,
                                           @RequestParam(required = false) String sort,
                                           @RequestParam(required = false) Integer page,
                                           @RequestParam(required = false) Integer limit) {
        return sneakerService.search(query, filters, sort, page, limit);
    }

    @GetMapping("/{id}")
    public SneakerSummaryDto getById(@PathVariable String id) {
        return sneakerService.getById(id);
    }

    @GetMapping("/brands")
    public ResponseEntity<String> getBrands(@RequestParam(required = false) Integer page,
                                             @RequestParam(required = false) Integer limit) {
        String body = sneakerCatalogClient.getBrands(page, limit);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(body);
    }
}
