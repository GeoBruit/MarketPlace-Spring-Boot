package com.geobruit.campus.market.project.controller;

import com.geobruit.campus.market.project.model.Image;
import com.geobruit.campus.market.project.model.Product;
import com.geobruit.campus.market.project.model.User;
import com.geobruit.campus.market.project.repository.ProductRepository;
import com.geobruit.campus.market.project.service.ImageService;
import com.geobruit.campus.market.project.service.ProductService;
import com.geobruit.campus.market.project.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class ProductController {

    ProductService productService;
    UserService userService;
    ImageService imageService;

    public ProductController(){}

    @Autowired
    public ProductController(ProductService theProductService, UserService theUserService, ImageService theImageService){

        this.productService = theProductService;
        this.userService = theUserService;
        this.imageService = theImageService;
    }


    @GetMapping("/add-product")
    public String showAddProductForm(Model model) {
        // Get auth object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        // Find user by username
        User user = userService.findUserByUsername(userName);

        if (user == null) {
            // Handle the case where the user is not found
            return "redirect:/login";
        }

        Long userId = user.getId();

        model.addAttribute("product", new Product());
        model.addAttribute("userId", userId);
        return "addProduct";
    }


    @PostMapping("/add-product/{userId}")
    public String saveProductForUser(@RequestParam("additionalImages") List<MultipartFile> additionalImages,
                                     @ModelAttribute Product product,
                                     @PathVariable Long userId) {

        User user = userService.findUserById(userId);

        if (user == null) {
            return "redirect:/login";
        }

        product.setUser(user);

        try {
            // Save the product with images
            productService.saveProductWithImages(product, additionalImages);

            return "redirect:/my-products";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    }


    @GetMapping("/my-products")
    public String showUserProductList(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User tempUser = userService.findUserByUsername(authentication.getName());
        List<Product> userProducts = tempUser.getProducts();
        model.addAttribute("products", userProducts);
        return "myProducts";
    }

    @GetMapping("/modify/{productId}")
    public String updateProductForm(@PathVariable Long productId, Model model){
        model.addAttribute("productId", productId);
        model.addAttribute("product", productService.getProductById(productId));
        return "updateProduct";
    }

    @PostMapping("/modify/{productId}")
    public String updateProduct(@ModelAttribute Product product, @PathVariable Long productId){

        productService.update(product, productId);
        return "redirect:/my-products";
    }

    @PostMapping("/delete/product/{productId}")
    public String deleteProduct(@PathVariable Long productId){

            //Getting longed in user, so we can remove the product from the user s list first
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            productService.deleteProductById(productId, authentication.getName());
            return "redirect:/my-products";
    }

    @GetMapping("/view/product/{productId}")
    public String showSingleProduct(@PathVariable Long productId, Model model){

        Product tempProduct = productService.getProductById(productId);
        model.addAttribute("product", tempProduct);

        return "singleProductView";
    }
}
