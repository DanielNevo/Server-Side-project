package com.example.restapp;

import jdk.jfr.DataAmount;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

/**
 * A Domain Object for our project.
 * Lombok library - generates: getters,setters,equals,HashCode,toString
 */
@Data
@Entity
public class Product {
    @Id @GeneratedValue private Long id;
    private String productName;
    private String category;
    private Double price;
    private String description;
    private Order order;


    public Product(String name, String category,Double price,String description){
        this.productName = name;
        this.category = category;
        this.price = price;
        this.description=description;
        this.order=null;
    }
    public Product(){}
    public Product(Product other){
        this.productName = other.productName;
        this.category = other.category;
        this.price = other.price;
        this.description = other.description;
        this.order = other.order;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
