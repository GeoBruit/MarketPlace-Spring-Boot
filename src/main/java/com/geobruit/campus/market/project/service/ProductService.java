package com.geobruit.campus.market.project.service;

import com.geobruit.campus.market.project.model.Product;
import com.geobruit.campus.market.project.model.User;
import com.geobruit.campus.market.project.repository.ProductRepository;
import com.geobruit.campus.market.project.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    ProductRepository productRepository;
    UserRepository userRepository;

    public ProductService(){}

    @Autowired
    public ProductService(ProductRepository theProductRepository, UserRepository theUserRepository){

        productRepository = theProductRepository;
        userRepository = theUserRepository;
    }

    public boolean saveProduct(Product temProduct){
        try{
            productRepository.save(temProduct);
            System.out.println("Product Saved to the database");
            return true;
        } catch (Exception e){
            e.getStackTrace();
            return false;
        }
    }

    public Product getProductById(Long theId){

        try {
            return productRepository.findProductById(theId);
        }catch (Exception e){
            e.getStackTrace();
            System.out.println("Product not in the database");
            return null;
        }
    }

    //TODO check if we can improve
    @Transactional
    public void deleteProductById(Long productId, String userName){

        try{
            //finding the user hat has the product
            User tempUser = userRepository.findByUsername(userName);
            //removing the product from the user products
            tempUser.removeProduct(productRepository.findProductById(productId));
            //deleting the product
            productRepository.deleteById(productId);
            System.out.println("Product with id: " + productId + " deleted");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Exception above!");
        }
    }

    public List<Product> findAllProducts(){
        try{
            return productRepository.findAll();
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public void update(Product theProduct, Long productId){

        Product product = productRepository.findProductById(productId);

        product.setName(theProduct.getName());
        product.setDescription(theProduct.getDescription());
        product.setPrice(theProduct.getPrice());
        productRepository.save(product);
    }
}
