package com.example.Ecommerce.Exceptions;

public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException(String id) {
        super("Product already exists with id: " + id);
    }
}
