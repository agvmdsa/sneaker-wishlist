package com.agvms.sneakerwishlist.controller;

import com.agvms.sneakerwishlist.client.SneakerCatalogClient;
import com.agvms.sneakerwishlist.dto.ErrorResponseDto;
import com.agvms.sneakerwishlist.dto.SneakerSummaryDto;
import com.agvms.sneakerwishlist.usecase.sneaker.GetSneakerByIdUseCase;
import com.agvms.sneakerwishlist.usecase.sneaker.SearchSneakersUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Sneakers", description = "Thin proxy to the KicksDB external catalog")
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

    @Operation(summary = "Search sneakers", description = "Proxies KicksDB's StockX-backed search, filtering out non-sneaker results (apparel, etc).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Search results"),
            @ApiResponse(responseCode = "502", description = "Invalid/unparseable response from the external API",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/search")
    public List<SneakerSummaryDto> search(@RequestParam(required = false) String query,
                                           @RequestParam(required = false) String filters,
                                           @RequestParam(required = false) String sort,
                                           @RequestParam(required = false) Integer page,
                                           @RequestParam(required = false) Integer limit) {
        return searchSneakersUseCase.execute(query, filters, sort, page, limit);
    }

    @Operation(summary = "Get a sneaker by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sneaker detail"),
            @ApiResponse(responseCode = "502", description = "Invalid/unparseable response from the external API",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/{id}")
    public SneakerSummaryDto getById(@PathVariable String id) {
        return getSneakerByIdUseCase.execute(id);
    }

    @Operation(summary = "List brands", description = "Raw passthrough to KicksDB, cached — brand data rarely changes.")
    @ApiResponse(responseCode = "200", description = "Raw KicksDB brand list")
    @GetMapping("/brands")
    public ResponseEntity<String> getBrands(@RequestParam(required = false) Integer page,
                                             @RequestParam(required = false) Integer limit) {
        String body = sneakerCatalogClient.getBrands(page, limit);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(body);
    }
}
