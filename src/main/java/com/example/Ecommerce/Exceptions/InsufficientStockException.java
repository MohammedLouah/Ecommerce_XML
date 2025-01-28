package com.example.Ecommerce.Exceptions;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String productId, int requested, int available) {
        super(String.format("Insufficient stock for product %s: requested %d, available %d",
                productId, requested, available));
    }
}
