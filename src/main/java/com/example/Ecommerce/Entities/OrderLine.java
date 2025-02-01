package com.example.Ecommerce.Entities;

import lombok.Data;
import javax.xml.bind.annotation.*;

@Data
@XmlRootElement(name = "orderLine")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderLine {
    @XmlElement
    private String id;

    @XmlElement
    private String productId;

    @XmlElement
    private int quantity;

    @XmlElement
    private double discount;

    public double calculateTotal(double productPrice) {
        double priceWithDiscount = productPrice * (1 - (this.discount / 100));
        return priceWithDiscount * this.quantity;
    }

    public void updateQuantity(int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.quantity = newQuantity;
    }

    public void applyDiscount(double discountPercentage) {
        if (discountPercentage < 0 || discountPercentage > 100) {
            throw new IllegalArgumentException("Discount must be between 0 and 100");
        }
        this.discount = discountPercentage;
    }
}
