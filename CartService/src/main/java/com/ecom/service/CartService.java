package com.ecom.service;

import com.ecom.client.InventoryServiceClient;
import com.ecom.client.ProductServiceClient;
import com.ecom.dto.*;
import com.ecom.model.Cart;
import com.ecom.model.CartItem;
import com.ecom.repo.CartItemRepository;
import com.ecom.repo.CartRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

//
//    private final CartRepository cartRepository;
//    private final ProductServiceClient productServiceClient;
//    private final CartItemRepository cartItemRepository;
//   private final InventoryServiceClient inventoryServiceClient;
//
//    public CartService(CartRepository cartRepository,
//                       ProductServiceClient productServiceClient,
//                       CartItemRepository cartItemRepository,InventoryServiceClient inventoryServiceClient) {
//        this.cartRepository = cartRepository;
//        this.productServiceClient = productServiceClient;
//        this.cartItemRepository = cartItemRepository;
//        this.inventoryServiceClient = inventoryServiceClient;
//    }
//
//    public CartResponseDto createCart(CartRequestDto cartRequestDTO) {
//        // Step 1: Create and save cart first
//        Cart cart = new Cart();
//        cart.setUserId(cartRequestDTO.getUserId());
//        cart.setCreatedAt(LocalDateTime.now());
//        cart.setUpdatedAt(LocalDateTime.now());
//
//        cart = cartRepository.save(cart); // Now it has a valid ID for DBRef
//
//        // Step 2: Create cart items and assign saved cart
//        List<CartItem> items = new ArrayList<>();
//        for (CartItemRequestDto itemRequest : cartRequestDTO.getItems()) {
//            CartItem item = createCartItemFromProductService(cart, itemRequest.getProductId(), itemRequest.getQuantity());
//            items.add(item);
//        }
//
//        cartItemRepository.saveAll(items); // Save all items after setting valid cart
//
//        cart.setCartItems(items); // Optional - to return in response
//        return mapToCartResponseDTO(cart);
//    }
//
//    private CartItem createCartItemFromProductService(Cart cart, Long productId, Integer quantity) {
////        ProductDto product = productServiceClient.getProductById(productId);
////        if (product == null) {
////            throw new RuntimeException("Product not found with ID: " + productId);
////        }
////
////        CartItem item = new CartItem();
////        item.setCart(cart); // Must have non-null ID
////        item.setProductId(product.getId());
////        item.setProductName(product.getName());
////        item.setUnitPrice(product.getPrice());
////        item.setQuantity(quantity);
////        return item;
//        ProductDto product = productServiceClient.getProductById(productId);
//        if (product == null) {
//            throw new RuntimeException("Product not found with ID: " + productId);
//        }
//
//        InventoryDto inventory = inventoryServiceClient.getInventoryByProductId(productId.toString());
//        if (inventory.getQuantity() < quantity) {
//            throw new RuntimeException("Insufficient inventory for product ID: " + productId);
//        }
//
//        inventoryServiceClient.reduceStock(productId.toString(), quantity); // Reduce stock
//
//        CartItem item = new CartItem();
//        item.setCart(cart);
//        item.setProductId(product.getId());
//        item.setProductName(product.getName());
//        item.setUnitPrice(product.getPrice());
//        item.setQuantity(quantity);
//        return item;
//    }
//    private CartResponseDto mapToCartResponseDTO(Cart cart) {
//        CartResponseDto response = new CartResponseDto();
//        response.setCartId(cart.getCartId());
//        response.setUserId(cart.getUserId());
//
//        List<CartItemResponseDto> itemResponses = cart.getCartItems().stream().map(item -> {
//            CartItemResponseDto dto = new CartItemResponseDto();
//            dto.setProductId(item.getProductId());
//            dto.setProductName(item.getProductName());
//            dto.setUnitPrice(item.getUnitPrice());
//            dto.setQuantity(item.getQuantity());
//            return dto;
//        }).collect(Collectors.toList());
//
//        response.setItems(itemResponses);
//        return response;
//    }
//
//    public List<CartItem> getCartItemsByUserId(Integer userId) {
//        Cart cart = cartRepository.findByUserId(userId);
//        return cart != null ? cartItemRepository.findByCart(cart) : new ArrayList<>();
//    }
//
//    public void clearCart(Integer userId) {
//        Cart cart = cartRepository.findByUserId(userId);
//        if (cart != null) {
//            cartItemRepository.deleteByCart(cart);
//        }
//    }
private final CartRepository cartRepository;
    private final ProductServiceClient productServiceClient;
    private final CartItemRepository cartItemRepository;
    private final InventoryServiceClient inventoryServiceClient;

    public CartService(CartRepository cartRepository,
                       ProductServiceClient productServiceClient,
                       CartItemRepository cartItemRepository,
                       InventoryServiceClient inventoryServiceClient) {
        this.cartRepository = cartRepository;
        this.productServiceClient = productServiceClient;
        this.cartItemRepository = cartItemRepository;
        this.inventoryServiceClient = inventoryServiceClient;
    }

    public CartResponseDto createCart(CartRequestDto cartRequestDTO) {
        // Step 1: Create and save cart first
//        Cart cart = new Cart();
//        cart.setUserId(cartRequestDTO.getUserId());
//        cart.setCreatedAt(LocalDateTime.now());
//        cart.setUpdatedAt(LocalDateTime.now());
//        cart = cartRepository.save(cart); // Now it has a valid ID
//
//        List<CartItem> items = new ArrayList<>();
//
//        // Step 2: For each item, reserve inventory and create cart item
//        for (CartItemRequestDto itemRequest : cartRequestDTO.getItems()) {
//            // Reserve inventory for this cart and product
//            boolean reserved = inventoryServiceClient.reserveInventory(
//                    cart.getCartId().toString(),
//                    itemRequest.getProductId().toString(),
//                    itemRequest.getQuantity());
//
//            if (!reserved) {
//                throw new RuntimeException("Unable to reserve stock for product ID: " + itemRequest.getProductId());
//            }
//
//            // Create cart item
//            CartItem item = createCartItemFromProductService(cart, itemRequest.getProductId(), itemRequest.getQuantity());
//            items.add(item);
//        }
//
//        cartItemRepository.saveAll(items);
//        cart.setCartItems(items);
//
//        return mapToCartResponseDTO(cart);

        Cart existingCart = cartRepository.findByUserId(cartRequestDTO.getUserId());
//        if (existingCart != null) {
//            Duration duration = Duration.between(existingCart.getCreatedAt(), LocalDateTime.now());
//            if (duration.toMinutes() > 10) {
//                cartItemRepository.deleteByCart(existingCart);
//                cartRepository.delete(existingCart);
//            } else {
//                // Prevent creating duplicate carts if an active cart exists
//                throw new RuntimeException("You already have an active cart. Please complete your order or wait for it to expire.");
//            }
//        }
        if (existingCart != null) {
            // Always delete the old cart and its items
            cartItemRepository.deleteByCart(existingCart);
            cartRepository.delete(existingCart);
        }


        // Step 1: Create and save new cart
        Cart cart = new Cart();
        cart.setUserId(cartRequestDTO.getUserId());
        cart.setCreatedAt(LocalDateTime.now());
        cart.setUpdatedAt(LocalDateTime.now());
        cart = cartRepository.save(cart); // Now it has a valid ID

        List<CartItem> items = new ArrayList<>();

        // Step 2: Try to reserve and create cart items
        for (CartItemRequestDto itemRequest : cartRequestDTO.getItems()) {
            boolean reserved = inventoryServiceClient.reserveInventory(
                    cart.getCartId().toString(),
                    itemRequest.getProductId().toString(),
                    itemRequest.getQuantity());

            if (!reserved) {
                // Rollback the cart and any reserved inventory
                cartItemRepository.deleteByCart(cart);
                cartRepository.delete(cart);
                inventoryServiceClient.releaseReservationsForCart(cart.getCartId().toString());

                throw new RuntimeException("Unable to reserve stock for product ID: " + itemRequest.getProductId());
            }

            CartItem item = createCartItemFromProductService(cart, itemRequest.getProductId(), itemRequest.getQuantity());
            items.add(item);
        }

        cartItemRepository.saveAll(items);
        cart.setCartItems(items);

        return mapToCartResponseDTO(cart);

    }

    private CartItem createCartItemFromProductService(Cart cart, Long productId, Integer quantity) {
        ProductDto product = productServiceClient.getProductById(productId);
        if (product == null) {
            throw new RuntimeException("Product not found with ID: " + productId);
        }

        CartItem item = new CartItem();
        item.setCart(cart);
        item.setProductId(product.getId());
        item.setProductName(product.getName());
        item.setUnitPrice(product.getPrice());
        item.setQuantity(quantity);
        return item;
    }

    private CartResponseDto mapToCartResponseDTO(Cart cart) {
        CartResponseDto response = new CartResponseDto();
        response.setCartId(cart.getCartId());
        response.setUserId(cart.getUserId());

        List<CartItemResponseDto> itemResponses = cart.getCartItems().stream().map(item -> {
            CartItemResponseDto dto = new CartItemResponseDto();
            dto.setProductId(item.getProductId());
            dto.setProductName(item.getProductName());
            dto.setUnitPrice(item.getUnitPrice());
            dto.setQuantity(item.getQuantity());
            dto.setCartId(cart.getCartId());
            return dto;
        }).collect(Collectors.toList());

        response.setItems(itemResponses);
        return response;
    }
//
//    public List<CartItem> getCartItemsByUserId(Integer userId) {
//        Cart cart = cartRepository.findByUserId(userId);
//        return cart != null ? cartItemRepository.findByCart(cart) : new ArrayList<>();
//    }

    public List<CartItemDto.CartItemDTO> getCartItemsByUserId(Integer userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) return new ArrayList<>();

        List<CartItem> items = cartItemRepository.findByCart(cart);
        return items.stream()
                .map(CartItemDto.CartItemDTO::new)  // Make sure you're mapping to CartItemDTO
                .collect(Collectors.toList());
    }




    public void clearCart(Integer userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart.getCartId() == null) {
            throw new RuntimeException("Cart ID is null for userId: " + userId);
        }
        if (cart != null) {
            // Release inventory reservation before deleting cart items
            inventoryServiceClient.releaseReservationsForCart(cart.getCartId().toString());

            cartItemRepository.deleteByCart(cart);


            // Delete the cart
            cartRepository.delete(cart);
        }
    }
}
