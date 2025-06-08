//package com.ecom.service;
//
//import com.ecom.model.Cart;
//import com.ecom.model.CartItem;
//import com.ecom.repo.CartItemRepository;
//import com.ecom.repo.CartRepository;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class CartItemService {
//    private CartRepository cartRepository;
//    private CartItemRepository cartItemRepository;
//    public List<CartItem> getCartItemsByUserId(Long userId) {
//        Cart cart = cartRepository.findByUserId(userId.intValue());
//        return cart != null ? cartItemRepository.findByCart(cart) : new ArrayList<>();
//    }
//
//    public void clearCart(Long userId) {
//        Cart cart = cartRepository.findByUserId(userId.intValue());
//        if (cart != null) {
//            cartItemRepository.deleteByCart(cart);
//        }
//    }
//}
