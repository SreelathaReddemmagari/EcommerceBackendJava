package com.ecom.controller;

import com.ecom.dto.CartItemDto;
import com.ecom.dto.CartItemRequestDto;
import com.ecom.dto.CartRequestDto;
import com.ecom.dto.CartResponseDto;
import com.ecom.model.CartItem;
import com.ecom.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping

    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CartResponseDto> createCart(@RequestBody CartRequestDto cartRequestDto) {
        CartResponseDto response = cartService.createCart(cartRequestDto);
        return ResponseEntity.ok(response);
    }

    // Get all items in the user's cart
    @GetMapping("/{userId}/items")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<CartItemDto.CartItemDTO>> getCartItems(@PathVariable Integer userId) {
        List<CartItemDto.CartItemDTO> items = cartService.getCartItemsByUserId(userId);
        return ResponseEntity.ok(items);
    }


    // Clear the user's cart
    @DeleteMapping("/{userId}")

    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> clearCart(@PathVariable Integer userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
