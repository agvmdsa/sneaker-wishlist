package com.agvms.sneakerwishlist.controller;

import com.agvms.sneakerwishlist.dto.WishlistItemCreateDto;
import com.agvms.sneakerwishlist.dto.WishlistItemDto;
import com.agvms.sneakerwishlist.dto.WishlistItemUpdateDto;
import com.agvms.sneakerwishlist.entity.WishlistStatus;
import com.agvms.sneakerwishlist.service.WishlistService;
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

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping
    public ResponseEntity<WishlistItemDto> addItem(@Valid @RequestBody WishlistItemCreateDto dto) {
        WishlistItemDto created = wishlistService.addItem(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/check")
    public ResponseEntity<Set<String>> checkAlreadyInCollection(@RequestBody List<String> externalSneakerIds) {
        return ResponseEntity.ok(wishlistService.findAlreadyInCollection(externalSneakerIds));
    }

    @GetMapping
    public Page<WishlistItemDto> list(@RequestParam(required = false) WishlistStatus status,
                                       @RequestParam(required = false) String tag,
                                       Pageable pageable) {
        return wishlistService.list(status, tag, pageable);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<WishlistItemDto> updateItem(@PathVariable Long id,
                                                       @RequestBody WishlistItemUpdateDto dto) {
        return ResponseEntity.ok(wishlistService.updateItem(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        wishlistService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
