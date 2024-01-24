package com.geobruit.campus.market.project.repository;

import com.geobruit.campus.market.project.model.Image;
import com.geobruit.campus.market.project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
