package org.example.controller;

import org.example.model.Product;
import org.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public String manageProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin/manage-products";
    }

    @GetMapping("/products/new")
    public String showProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "admin/product-form";
    }

    @PostMapping("/products")
    public String saveProduct(@ModelAttribute Product product) {
        productService.saveProduct(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/products/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "admin/product-form";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }
}