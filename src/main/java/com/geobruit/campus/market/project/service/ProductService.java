package com.geobruit.campus.market.project.service;

import com.geobruit.campus.market.project.model.Image;
import com.geobruit.campus.market.project.model.Product;
import com.geobruit.campus.market.project.model.User;
import com.geobruit.campus.market.project.repository.ProductRepository;
import com.geobruit.campus.market.project.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.juli.logging.Log;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    ProductRepository productRepository;
    UserRepository userRepository;
    ImageService imageService;
    //debugging
    public ProductService(){}

    @Autowired
    public ProductService(ProductRepository theProductRepository, UserRepository theUserRepository, ImageService theImageService){

        productRepository = theProductRepository;
        userRepository = theUserRepository;
        imageService = theImageService;
    }

    public Product saveProduct(Product tempProduct) {
        try {
            return productRepository.save(tempProduct);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public Product saveProductWithImages(Product product, List<MultipartFile> additionalImages) throws IOException {
        // Create a list to hold the images
        List<Image> savedImages = new ArrayList<>();

        // Save additional images
        for (MultipartFile additionalImage : additionalImages) {
            if (!additionalImage.isEmpty()) {
                String additionalImagePath = imageService.saveImageToFileSystem(additionalImage);
                Image additionalProductImage = new Image();
                additionalProductImage.setImagePath(additionalImagePath);
                additionalProductImage.setProduct(product);
                savedImages.add(additionalProductImage);
            }
        }

        // Set the saved images to the product
        product.setImages(savedImages);

        // Save the product with images
        return productRepository.save(product);
    }

//    @Transactional
//    public Product saveProductWithImages(Product product, MultipartFile mainImage, List<MultipartFile> additionalImages) throws IOException {
//        // Create a list to hold the images
//        List<Image> savedImages = new ArrayList<>();
//
//        // Save the main product image
//        if (!mainImage.isEmpty()) {
//            String mainImagePath = imageService.saveImageToFileSystem(mainImage);
//            Image mainProductImage = new Image();
//            mainProductImage.setImagePath(mainImagePath);
//            mainProductImage.setProduct(product);
//            savedImages.add(mainProductImage);
//        }
//
//        // Save additional images
//        for (MultipartFile additionalImage : additionalImages) {
//            if (!additionalImage.isEmpty()) {
//                String additionalImagePath = imageService.saveImageToFileSystem(additionalImage);
//                Image additionalProductImage = new Image();
//                additionalProductImage.setImagePath(additionalImagePath);
//                additionalProductImage.setProduct(product);
//                savedImages.add(additionalProductImage);
//            }
//        }
//
//        // Set the saved images to the product
//        product.setImages(savedImages);
//
//        // Save the product with images
//        return productRepository.save(product);
//    }

    // Other methods...

    public void update(Product theProduct) {
        try {
            productRepository.save(theProduct);
        } catch (Exception e) {
            e.printStackTrace();
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
