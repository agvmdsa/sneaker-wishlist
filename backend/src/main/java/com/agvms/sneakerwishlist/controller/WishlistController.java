package com.agvms.sneakerwishlist.controller;

import com.agvms.sneakerwishlist.dto.ErrorResponseDto;
import com.agvms.sneakerwishlist.dto.PriceHistoryDto;
import com.agvms.sneakerwishlist.dto.StatsDto;
import com.agvms.sneakerwishlist.dto.StatusUpdateDto;
import com.agvms.sneakerwishlist.dto.TagDto;
import com.agvms.sneakerwishlist.dto.WishlistItemCreateDto;
import com.agvms.sneakerwishlist.dto.WishlistItemDto;
import com.agvms.sneakerwishlist.dto.WishlistItemUpdateDto;
import com.agvms.sneakerwishlist.entity.WishlistStatus;
import com.agvms.sneakerwishlist.usecase.wishlist.AddWishlistItemUseCase;
import com.agvms.sneakerwishlist.usecase.wishlist.AssignTagToWishlistItemUseCase;
import com.agvms.sneakerwishlist.usecase.wishlist.CheckAlreadyInCollectionUseCase;
import com.agvms.sneakerwishlist.usecase.wishlist.DeleteWishlistItemUseCase;
import com.agvms.sneakerwishlist.usecase.wishlist.GetPriceHistoryUseCase;
import com.agvms.sneakerwishlist.usecase.wishlist.GetWishlistStatsUseCase;
import com.agvms.sneakerwishlist.usecase.wishlist.ListWishlistItemsUseCase;
import com.agvms.sneakerwishlist.usecase.wishlist.RemoveTagFromWishlistItemUseCase;
import com.agvms.sneakerwishlist.usecase.wishlist.UpdateWishlistItemUseCase;
import com.agvms.sneakerwishlist.usecase.wishlist.UpdateWishlistStatusUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/wishlist")
@Tag(name = "Wishlist", description = "Manage the personal sneaker collection")
public class WishlistController {

    private final AddWishlistItemUseCase addWishlistItemUseCase;
    private final CheckAlreadyInCollectionUseCase checkAlreadyInCollectionUseCase;
    private final ListWishlistItemsUseCase listWishlistItemsUseCase;
    private final UpdateWishlistItemUseCase updateWishlistItemUseCase;
    private final DeleteWishlistItemUseCase deleteWishlistItemUseCase;
    private final UpdateWishlistStatusUseCase updateWishlistStatusUseCase;
    private final AssignTagToWishlistItemUseCase assignTagToWishlistItemUseCase;
    private final RemoveTagFromWishlistItemUseCase removeTagFromWishlistItemUseCase;
    private final GetWishlistStatsUseCase getWishlistStatsUseCase;
    private final GetPriceHistoryUseCase getPriceHistoryUseCase;

    public WishlistController(AddWishlistItemUseCase addWishlistItemUseCase,
                               CheckAlreadyInCollectionUseCase checkAlreadyInCollectionUseCase,
                               ListWishlistItemsUseCase listWishlistItemsUseCase,
                               UpdateWishlistItemUseCase updateWishlistItemUseCase,
                               DeleteWishlistItemUseCase deleteWishlistItemUseCase,
                               UpdateWishlistStatusUseCase updateWishlistStatusUseCase,
                               AssignTagToWishlistItemUseCase assignTagToWishlistItemUseCase,
                               RemoveTagFromWishlistItemUseCase removeTagFromWishlistItemUseCase,
                               GetWishlistStatsUseCase getWishlistStatsUseCase,
                               GetPriceHistoryUseCase getPriceHistoryUseCase) {
        this.addWishlistItemUseCase = addWishlistItemUseCase;
        this.checkAlreadyInCollectionUseCase = checkAlreadyInCollectionUseCase;
        this.listWishlistItemsUseCase = listWishlistItemsUseCase;
        this.updateWishlistItemUseCase = updateWishlistItemUseCase;
        this.deleteWishlistItemUseCase = deleteWishlistItemUseCase;
        this.updateWishlistStatusUseCase = updateWishlistStatusUseCase;
        this.assignTagToWishlistItemUseCase = assignTagToWishlistItemUseCase;
        this.removeTagFromWishlistItemUseCase = removeTagFromWishlistItemUseCase;
        this.getWishlistStatsUseCase = getWishlistStatsUseCase;
        this.getPriceHistoryUseCase = getPriceHistoryUseCase;
    }

    @Operation(summary = "Add a sneaker to the collection", description = "Idempotent on (external sneaker id, size) — retrying with the same pair returns the existing item instead of duplicating it.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created (or already existed)"),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping
    public ResponseEntity<WishlistItemDto> addItem(@Valid @RequestBody WishlistItemCreateDto dto) {
        WishlistItemDto created = addWishlistItemUseCase.execute(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Check which sneakers are already in the collection", description = "Batch check for a list of external sneaker ids — avoids one call per item on the search results page.")
    @ApiResponse(responseCode = "200", description = "Subset of the given ids that already exist as active items")
    @PostMapping("/check")
    public ResponseEntity<Set<String>> checkAlreadyInCollection(@RequestBody List<String> externalSneakerIds) {
        return ResponseEntity.ok(checkAlreadyInCollectionUseCase.execute(externalSneakerIds));
    }

    @Operation(summary = "List the collection", description = "Paginated, filterable by status and tag. Defaults to WANT and OWNED items when no status is given.")
    @ApiResponse(responseCode = "200", description = "Paginated collection")
    @GetMapping
    public Page<WishlistItemDto> list(@RequestParam(required = false) WishlistStatus status,
                                       @RequestParam(required = false) String tag,
                                       Pageable pageable) {
        return listWishlistItemsUseCase.execute(status, tag, pageable);
    }

    @Operation(summary = "Edit a collection item's personal data", description = "Only size and notes are editable here.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated"),
            @ApiResponse(responseCode = "404", description = "Item not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<WishlistItemDto> updateItem(@PathVariable Long id,
                                                       @RequestBody WishlistItemUpdateDto dto) {
        return ResponseEntity.ok(updateWishlistItemUseCase.execute(id, dto));
    }

    @Operation(summary = "Remove an item from the collection", description = "Soft delete — the row is kept, marked as deleted.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deleted"),
            @ApiResponse(responseCode = "404", description = "Item not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        deleteWishlistItemUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Change an item's status", description = "Valid transitions: WANT→OWNED, OWNED→SOLD, OWNED→DONATED. Requires price_paid when moving to OWNED. Idempotent no-op if already at the target status.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status updated (or already at the target status)"),
            @ApiResponse(responseCode = "400", description = "Invalid transition, or missing price_paid when moving to OWNED",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Item not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<WishlistItemDto> updateStatus(@PathVariable Long id,
                                                         @Valid @RequestBody StatusUpdateDto dto) {
        return ResponseEntity.ok(updateWishlistStatusUseCase.execute(id, dto));
    }

    @Operation(summary = "Assign a tag to an item", description = "Creates the tag on the fly if it doesn't exist yet.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tag assigned"),
            @ApiResponse(responseCode = "404", description = "Item not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping("/{id}/tags")
    public ResponseEntity<WishlistItemDto> assignTag(@PathVariable Long id, @RequestBody TagDto dto) {
        return ResponseEntity.ok(assignTagToWishlistItemUseCase.execute(id, dto));
    }

    @Operation(summary = "Remove a tag from an item")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Tag removed"),
            @ApiResponse(responseCode = "404", description = "Item not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/{id}/tags/{tagId}")
    public ResponseEntity<Void> removeTag(@PathVariable Long id, @PathVariable Long tagId) {
        removeTagFromWishlistItemUseCase.execute(id, tagId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get an item's price history", description = "Chronological list of recorded price checks for the item.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Price history, oldest first"),
            @ApiResponse(responseCode = "404", description = "Item not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/{id}/price-history")
    public List<PriceHistoryDto> priceHistory(@PathVariable Long id) {
        return getPriceHistoryUseCase.execute(id);
    }

    @Operation(summary = "Get collection statistics", description = "Total active items, total spent, estimated wishlist value, and the most frequent brand.")
    @ApiResponse(responseCode = "200", description = "Stats computed over active items")
    @GetMapping("/stats")
    public StatsDto stats() {
        return getWishlistStatsUseCase.execute();
    }
}
