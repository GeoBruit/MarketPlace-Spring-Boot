package com.geobruit.campus.market.project.controller;

import com.geobruit.campus.market.project.model.Product;
import com.geobruit.campus.market.project.model.User;
import com.geobruit.campus.market.project.repository.ProductRepository;
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

import java.util.List;

@Controller
public class ProductController {

    ProductService productService;
    UserService userService;

    public ProductController(){}

    @Autowired
    public ProductController(ProductService theProductService, UserService theUserService){

        this.productService = theProductService;
        this.userService = theUserService;
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
    public String saveProductForUser(@ModelAttribute Product product, @PathVariable Long userId){
            product.setUser(userService.findUserById(userId));
            productService.saveProduct(product);
            return "redirect:/my-products";
    }


    //old way just keep ot in any case
    //TODO check to  see if you still need it
//    @GetMapping("/product/{userId}")
//    public String productList(@PathVariable long userId, Model model){
//
//
//        User tempUser = userService.findUserById(userId);
//        List<Product> productList = tempUser.getProducts();
//        model.addAttribute("products", productList);
//        return "productsList";
//    }

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
}
