//package com.example.restapp;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//@ControllerAdvice
//public class ProductAdvice {
//    @ResponseBody
//    @ExceptionHandler(ProductNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    String productNotFoundHandler(ProductNotFoundException pnf){
//        return pnf.getMessage();
//    }
//    public static class ProductNotFoundException extends RuntimeException {
//
//        public ProductNotFoundException(Long id) {
//            super("There is no product corresponding to id = " + id);
//        }
//    }
//}
