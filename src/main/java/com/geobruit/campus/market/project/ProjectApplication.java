package com.geobruit.campus.market.project;

import com.geobruit.campus.market.project.model.Product;
import com.geobruit.campus.market.project.model.User;
import com.geobruit.campus.market.project.service.ProductService;
import com.geobruit.campus.market.project.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class ProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner demo(UserService userService){
//		return args -> {
//			// Test addUser
//			User tempUser = new User("Jhon Doe", "Test@test.com", "1234567");
//			userService.addUser(tempUser);
//
//			//test to find it
//			User tempUser2 = userService.findUserById(10L);
//			System.out.println("checker");
//			System.out.println(tempUser2 == null? "User not found!": tempUser2.toString());
//
//			System.out.println("__________________________________");
//
//			List<User> userList = userService.findAllUsers();
//			userList.forEach(x -> System.out.println(x.getUsername()));
//		};
//	}

//	@Bean
//	public CommandLineRunner demo2(ProductService productService){
//		return args -> {
//			//test add product
//			Product tempProduct = new Product("Pasta", "Good and delicious", 27);
//			productService.saveProduct(tempProduct);
//
//			//test find product by id;
//
//			Product tempProduct2 = productService.getProductById(1L);
//			System.out.println(tempProduct2 == null? "Product not found!": tempProduct2.toString());
//		};
//	}


}
