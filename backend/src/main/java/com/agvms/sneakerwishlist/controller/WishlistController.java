package com.agvms.sneakerwishlist.controller;

import com.agvms.sneakerwishlist.dto.StatusUpdateDto;
import com.agvms.sneakerwishlist.dto.WishlistItemCreateDto;
import com.agvms.sneakerwishlist.dto.WishlistItemDto;
import com.agvms.sneakerwishlist.dto.WishlistItemUpdateDto;
import com.agvms.sneakerwishlist.entity.WishlistStatus;
import com.agvms.sneakerwishlist.usecase.wishlist.AddWishlistItemUseCase;
import com.agvms.sneakerwishlist.usecase.wishlist.CheckAlreadyInCollectionUseCase;
import com.agvms.sneakerwishlist.usecase.wishlist.DeleteWishlistItemUseCase;
import com.agvms.sneakerwishlist.usecase.wishlist.ListWishlistItemsUseCase;
import com.agvms.sneakerwishlist.usecase.wishlist.UpdateWishlistItemUseCase;
import com.agvms.sneakerwishlist.usecase.wishlist.UpdateWishlistStatusUseCase;
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
public class WishlistController {

    private final AddWishlistItemUseCase addWishlistItemUseCase;
    private final CheckAlreadyInCollectionUseCase checkAlreadyInCollectionUseCase;
    private final ListWishlistItemsUseCase listWishlistItemsUseCase;
    private final UpdateWishlistItemUseCase updateWishlistItemUseCase;
    private final DeleteWishlistItemUseCase deleteWishlistItemUseCase;
    private final UpdateWishlistStatusUseCase updateWishlistStatusUseCase;

    public WishlistController(AddWishlistItemUseCase addWishlistItemUseCase,
                               CheckAlreadyInCollectionUseCase checkAlreadyInCollectionUseCase,
                               ListWishlistItemsUseCase listWishlistItemsUseCase,
                               UpdateWishlistItemUseCase updateWishlistItemUseCase,
                               DeleteWishlistItemUseCase deleteWishlistItemUseCase,
                               UpdateWishlistStatusUseCase updateWishlistStatusUseCase) {
        this.addWishlistItemUseCase = addWishlistItemUseCase;
        this.checkAlreadyInCollectionUseCase = checkAlreadyInCollectionUseCase;
        this.listWishlistItemsUseCase = listWishlistItemsUseCase;
        this.updateWishlistItemUseCase = updateWishlistItemUseCase;
        this.deleteWishlistItemUseCase = deleteWishlistItemUseCase;
        this.updateWishlistStatusUseCase = updateWishlistStatusUseCase;
    }

    @PostMapping
    public ResponseEntity<WishlistItemDto> addItem(@Valid @RequestBody WishlistItemCreateDto dto) {
        WishlistItemDto created = addWishlistItemUseCase.execute(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/check")
    public ResponseEntity<Set<String>> checkAlreadyInCollection(@RequestBody List<String> externalSneakerIds) {
        return ResponseEntity.ok(checkAlreadyInCollectionUseCase.execute(externalSneakerIds));
    }

    @GetMapping
    public Page<WishlistItemDto> list(@RequestParam(required = false) WishlistStatus status,
                                       @RequestParam(required = false) String tag,
                                       Pageable pageable) {
        return listWishlistItemsUseCase.execute(status, tag, pageable);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<WishlistItemDto> updateItem(@PathVariable Long id,
                                                       @RequestBody WishlistItemUpdateDto dto) {
        return ResponseEntity.ok(updateWishlistItemUseCase.execute(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        deleteWishlistItemUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<WishlistItemDto> updateStatus(@PathVariable Long id,
                                                         @Valid @RequestBody StatusUpdateDto dto) {
        return ResponseEntity.ok(updateWishlistStatusUseCase.execute(id, dto));
    }
}
