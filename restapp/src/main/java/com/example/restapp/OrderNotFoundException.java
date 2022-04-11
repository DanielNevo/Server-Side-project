package com.example.restapp;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(Long id) {
        super("Order corresponding to id = " + id + " dose not exist");
    }
}
