package com.example.Ecommerce.Entities;

import lombok.Data;
import javax.xml.bind.annotation.*;

@Data
@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class Product {
    @XmlElement
    private String id;

    @XmlElement
    private String name;

    @XmlElement
    private String brand;

    @XmlElement
    private String categoryId;

    @XmlElement
    private double price;

    @XmlElement
    private String image;

    @XmlElement
    private String specifications;

    @XmlElement
    private int stock;


}

