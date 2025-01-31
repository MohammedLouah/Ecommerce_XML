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
}
