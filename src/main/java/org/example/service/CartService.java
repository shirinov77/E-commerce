package org.example.service;

import org.example.model.Cart;
import org.example.model.CartItem;
import org.example.model.Product;
import org.example.repository.CartRepository;
import org.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;

    public Cart getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = new Cart();
            // Yangi savatcha yaratish uchun foydalanuvchi kerak
            // Bu yerda foydalanuvchi ID dan User obyekti olinishi kerak
            // Ho‘sh, bu holatni keyinroq hal qilamiz
            throw new RuntimeException("Savatcha topilmadi va yangi yaratish uchun user kerak");
        }
        return cart;
    }

    public void addProductToCart(Long userId, Long productId, int quantity) {
        Cart cart = getCartByUserId(userId);
        Product product = productRepository.findById(productId);
        if (product != null && product.getStock() >= quantity) {
            CartItem item = new CartItem();
            item.setProduct(product);
            item.setQuantity(quantity);
            cart.addItem(item);
            cartRepository.save(cart);
        }
    }

    public void removeProductFromCart(Long userId, Long productId) {
        Cart cart = getCartByUserId(userId);
        cart.getCartItems().removeIf(item -> item.getProduct().getId().equals(productId));
        cartRepository.save(cart);
    }
}