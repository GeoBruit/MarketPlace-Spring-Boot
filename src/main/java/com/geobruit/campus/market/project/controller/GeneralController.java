package com.geobruit.campus.market.project.controller;

import com.geobruit.campus.market.project.model.Product;
import com.geobruit.campus.market.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class GeneralController {

    private ProductRepository productRepository;

    @Autowired
    public GeneralController(ProductRepository theProductRepository){
        productRepository = theProductRepository;
    }


    @GetMapping("/home")
    public String ShowHomePage(Model model){

        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);

        return "homePage";
    }
}
