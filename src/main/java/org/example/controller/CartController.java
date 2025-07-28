package org.example.controller;

import org.example.model.Cart;
import org.example.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping
    public String viewCart(@RequestParam Long userId, Model model) {
        Cart cart = cartService.getCartByUserId(userId);
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity) {
        cartService.addProductToCart(userId, productId, quantity);
        return "redirect:/cart?userId=" + userId;
    }

    @GetMapping("/remove")
    public String removeFromCart(@RequestParam Long userId, @RequestParam Long productId) {
        cartService.removeProductFromCart(userId, productId);
        return "redirect:/cart?userId=" + userId;
    }
}