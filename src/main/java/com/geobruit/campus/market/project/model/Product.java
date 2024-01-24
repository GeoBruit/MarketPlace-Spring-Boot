package com.geobruit.campus.market.project.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "products")
public class Product {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

//    @Column(name = "imagine_data", columnDefinition = "TEXT DEFAULT NULL")
//    private String imagineData;

    public Product(){}
    public Product(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
//        this.imagineData="";
    }

//    public String getImagineData() {
//        return imagineData;
//    }
//
//    public void setImagineData(String imagineData) {
//        this.imagineData = imagineData;
//    }

    public List<Image> getImages() {
        return images;
    }


    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void addImage(Image theImage){
        images.add(theImage);
        theImage.setProduct(this);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
