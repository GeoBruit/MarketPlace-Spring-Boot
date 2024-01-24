package com.geobruit.campus.market.project.service;

import com.geobruit.campus.market.project.model.Image;
import com.geobruit.campus.market.project.model.Product;
import com.geobruit.campus.market.project.repository.ProductRepository;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class ImageService {

    private final ProductRepository productRepository;

    //debugging

    @Autowired
    public ImageService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Image> saveImages(Long productId, List<MultipartFile> additionalImages) throws IOException {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }

        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            throw new IllegalArgumentException("Product not found with ID: " + productId);
        }

        List<Image> savedImages = new ArrayList<>();

        // Save additional images
        for (MultipartFile additionalImage : additionalImages) {
            if (!additionalImage.isEmpty()) {
                String additionalImagePath = saveImageToFileSystem(additionalImage);
                Image additionalProductImage = new Image();
                additionalProductImage.setImagePath(additionalImagePath);
                additionalProductImage.setProduct(product);
                savedImages.add(additionalProductImage);
            }
        }

        return savedImages;
    }
//    public List<Image> saveImages(Long productId, MultipartFile mainImage, List<MultipartFile> additionalImages) throws IOException {
//        if (productId == null) {
//            throw new IllegalArgumentException("Product ID cannot be null");
//        }
//
//        Product product = productRepository.findById(productId).orElse(null);
//
//        if (product == null) {
//            throw new IllegalArgumentException("Product not found with ID: " + productId);
//        }
//
//        List<Image> savedImages = new ArrayList<>();
//
//        // Save the main product image
//        if (!mainImage.isEmpty()) {
//            String mainImagePath = saveImageToFileSystem(mainImage);
//            Image mainProductImage = new Image();
//            mainProductImage.setImagePath(mainImagePath);
//            mainProductImage.setProduct(product);
//            savedImages.add(mainProductImage);
//        }
//
//        // Save additional images
//        for (MultipartFile additionalImage : additionalImages) {
//            if (!additionalImage.isEmpty()) {
//                String additionalImagePath = saveImageToFileSystem(additionalImage);
//                Image additionalProductImage = new Image();
//                additionalProductImage.setImagePath(additionalImagePath);
//                additionalProductImage.setProduct(product);
//                savedImages.add(additionalProductImage);
//            }
//        }


//        return savedImages;
//    }

    // Other methods...

    String saveImageToFileSystem(MultipartFile image) throws IOException {
        String uploadFolder = "C:\\Users\\georg\\Desktop\\project\\src\\main\\resources\\static\\productImage";
        String originalFilename = image.getOriginalFilename();
        String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;
        Path folderPath = Paths.get(uploadFolder).toAbsolutePath().normalize();
        Path filePath = folderPath.resolve(uniqueFilename);
        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);


        return "productImage/" + uniqueFilename;
    }
}