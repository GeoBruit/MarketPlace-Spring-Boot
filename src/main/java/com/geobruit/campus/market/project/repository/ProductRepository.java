package com.geobruit.campus.market.project.repository;

import com.geobruit.campus.market.project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findProductById(Long thId);
}
