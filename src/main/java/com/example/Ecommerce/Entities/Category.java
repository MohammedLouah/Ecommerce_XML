package com.example.Ecommerce.Entities;

import lombok.Data;
import javax.xml.bind.annotation.*;

@Data
@XmlRootElement(name = "category")
@XmlAccessorType(XmlAccessType.FIELD)
public class Category {
    @XmlElement
    private String id;

    @XmlElement
    private String name;

    @XmlElement
    private String description;
}
