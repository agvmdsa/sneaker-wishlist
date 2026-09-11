package com.agvms.sneakerwishlist.controller;

import com.agvms.sneakerwishlist.client.SneakerCatalogClient;
import com.agvms.sneakerwishlist.dto.SneakerSummaryDto;
import com.agvms.sneakerwishlist.usecase.sneaker.GetSneakerByIdUseCase;
import com.agvms.sneakerwishlist.usecase.sneaker.SearchSneakersUseCase;
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
    private final SearchSneakersUseCase searchSneakersUseCase;
    private final GetSneakerByIdUseCase getSneakerByIdUseCase;

    public SneakerController(SneakerCatalogClient sneakerCatalogClient,
                              SearchSneakersUseCase searchSneakersUseCase,
                              GetSneakerByIdUseCase getSneakerByIdUseCase) {
        this.sneakerCatalogClient = sneakerCatalogClient;
        this.searchSneakersUseCase = searchSneakersUseCase;
        this.getSneakerByIdUseCase = getSneakerByIdUseCase;
    }

    @GetMapping("/search")
    public List<SneakerSummaryDto> search(@RequestParam(required = false) String query,
                                           @RequestParam(required = false) String filters,
                                           @RequestParam(required = false) String sort,
                                           @RequestParam(required = false) Integer page,
                                           @RequestParam(required = false) Integer limit) {
        return searchSneakersUseCase.execute(query, filters, sort, page, limit);
    }

    @GetMapping("/{id}")
    public SneakerSummaryDto getById(@PathVariable String id) {
        return getSneakerByIdUseCase.execute(id);
    }

    @GetMapping("/brands")
    public ResponseEntity<String> getBrands(@RequestParam(required = false) Integer page,
                                             @RequestParam(required = false) Integer limit) {
        String body = sneakerCatalogClient.getBrands(page, limit);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(body);
    }
}
