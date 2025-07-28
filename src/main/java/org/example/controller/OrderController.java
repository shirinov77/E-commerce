package org.example.controller;

import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public String viewOrders(@RequestParam Long userId, Model model) {
        model.addAttribute("orders", orderService.getOrdersByUserId(userId));
        return "order-list";
    }

    @GetMapping("/create")
    public String createOrder(@RequestParam Long userId) {
        orderService.createOrder(userId);
        return "redirect:/orders?userId=" + userId;
    }
}