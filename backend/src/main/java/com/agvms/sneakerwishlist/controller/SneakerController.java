package com.agvms.sneakerwishlist.controller;

import com.agvms.sneakerwishlist.client.SneakerCatalogClient;
import com.agvms.sneakerwishlist.dto.ErrorResponseDto;
import com.agvms.sneakerwishlist.dto.SneakerSummaryDto;
import com.agvms.sneakerwishlist.dto.UpcomingReleasesDto;
import com.agvms.sneakerwishlist.usecase.sneaker.query.GetPopularSneakersQuery;
import com.agvms.sneakerwishlist.usecase.sneaker.query.GetPopularSneakersUseCase;
import com.agvms.sneakerwishlist.usecase.sneaker.query.GetSneakerByIdQuery;
import com.agvms.sneakerwishlist.usecase.sneaker.query.GetSneakerByIdUseCase;
import com.agvms.sneakerwishlist.usecase.sneaker.query.GetTrendingSneakersQuery;
import com.agvms.sneakerwishlist.usecase.sneaker.query.GetTrendingSneakersUseCase;
import com.agvms.sneakerwishlist.usecase.sneaker.query.GetUpcomingReleasesQuery;
import com.agvms.sneakerwishlist.usecase.sneaker.query.GetUpcomingReleasesUseCase;
import com.agvms.sneakerwishlist.usecase.sneaker.query.SearchSneakersQuery;
import com.agvms.sneakerwishlist.usecase.sneaker.query.SearchSneakersUseCase;
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
    private final GetPopularSneakersUseCase getPopularSneakersUseCase;
    private final GetTrendingSneakersUseCase getTrendingSneakersUseCase;
    private final GetUpcomingReleasesUseCase getUpcomingReleasesUseCase;

    public SneakerController(SneakerCatalogClient sneakerCatalogClient,
                              SearchSneakersUseCase searchSneakersUseCase,
                              GetSneakerByIdUseCase getSneakerByIdUseCase,
                              GetPopularSneakersUseCase getPopularSneakersUseCase,
                              GetTrendingSneakersUseCase getTrendingSneakersUseCase,
                              GetUpcomingReleasesUseCase getUpcomingReleasesUseCase) {
        this.sneakerCatalogClient = sneakerCatalogClient;
        this.searchSneakersUseCase = searchSneakersUseCase;
        this.getSneakerByIdUseCase = getSneakerByIdUseCase;
        this.getPopularSneakersUseCase = getPopularSneakersUseCase;
        this.getTrendingSneakersUseCase = getTrendingSneakersUseCase;
        this.getUpcomingReleasesUseCase = getUpcomingReleasesUseCase;
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
        return searchSneakersUseCase.execute(new SearchSneakersQuery(query, filters, sort, page, limit));
    }

    @Operation(summary = "Get a sneaker by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sneaker detail"),
            @ApiResponse(responseCode = "502", description = "Invalid/unparseable response from the external API",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/{id}")
    public SneakerSummaryDto getById(@PathVariable String id) {
        return getSneakerByIdUseCase.execute(new GetSneakerByIdQuery(id));
    }

    @Operation(summary = "List brands", description = "Raw passthrough to KicksDB, cached — brand data rarely changes.")
    @ApiResponse(responseCode = "200", description = "Raw KicksDB brand list")
    @GetMapping("/brands")
    public ResponseEntity<String> getBrands(@RequestParam(required = false) Integer page,
                                             @RequestParam(required = false) Integer limit) {
        String body = sneakerCatalogClient.getBrands(page, limit);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(body);
    }

    @Operation(summary = "Most popular sneakers", description = "Sneakers ranked by KicksDB popularity rank, for the discovery home hero section.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Popular sneakers"),
            @ApiResponse(responseCode = "502", description = "Invalid/unparseable response from the external API",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/popular")
    public List<SneakerSummaryDto> popular(@RequestParam(required = false) Integer limit) {
        return getPopularSneakersUseCase.execute(new GetPopularSneakersQuery(limit));
    }

    @Operation(summary = "Trending sneakers this week", description = "Sneakers with the most orders in the past week, re-ranked in memory from a rank-ordered candidate pool since KicksDB cannot sort/filter by weekly order volume.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trending sneakers"),
            @ApiResponse(responseCode = "502", description = "Invalid/unparseable response from the external API",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/trending")
    public List<SneakerSummaryDto> trending(@RequestParam(required = false) Integer limit) {
        return getTrendingSneakersUseCase.execute(new GetTrendingSneakersQuery(limit));
    }

    @Operation(summary = "Upcoming sneaker releases", description = "Sneakers not yet released, grouped into relative release-proximity windows since KicksDB never returns an exact release date.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Upcoming releases grouped by proximity"),
            @ApiResponse(responseCode = "502", description = "Invalid/unparseable response from the external API",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/upcoming-releases")
    public UpcomingReleasesDto upcomingReleases() {
        return getUpcomingReleasesUseCase.execute(new GetUpcomingReleasesQuery());
    }
}
