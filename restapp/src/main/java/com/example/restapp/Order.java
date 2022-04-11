package com.example.restapp;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {
    @Id @GeneratedValue private Long id;
    private LocalDate purchaseDate;
    private String title;
    private Double price;
    private ArrayList<Product> productList;

    public Order(Long id, LocalDate purchaseDate, String title, Double price, ArrayList<Product> productList) {
        this.id = id;
        this.purchaseDate = purchaseDate;
        this.title = title;
        this.price = price;
        this.productList=productList;
    }
    // the function add the product to list productList
    //then we update the price of the order

    public Order() {
    }

    public  Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }
}
