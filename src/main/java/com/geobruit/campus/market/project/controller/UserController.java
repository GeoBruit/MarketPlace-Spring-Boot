package com.geobruit.campus.market.project.controller;

import com.geobruit.campus.market.project.model.Product;
import com.geobruit.campus.market.project.model.User;
import com.geobruit.campus.market.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    UserService userService;

    //No args constructor
    public UserController(){}

    @Autowired
    public UserController(UserService theUserService){
        userService = theUserService;
    }

    @GetMapping("/list")
    public String getUsers(Model model) {
        List<User> userList = userService.findAllUsers();
        model.addAttribute("users", userList);
        return "userList";
    }
    @GetMapping("/user/{userId}")
    public String getSingleUser(@PathVariable Long userId, Model model){
        User tempUser = userService.findUserById(userId);
        model.addAttribute("user", tempUser);
        return "userView";
    }

    //todo check if needed
    @GetMapping("/user/{userId}/products")
    public String getUserProducts(@PathVariable Long userId, Model model) {
        List<Product> products = userService.getAllProductsForUser(userId);
        model.addAttribute("products", products);
        return "productList"; // Create a Thymeleaf template for product list
    }




//    @PostMapping("/addProduct")
//    public String addProduct(@ModelAttribute Product product) {
//        userService.addProduct(product);
//        return "redirect:/addProduct";
//    }
}
