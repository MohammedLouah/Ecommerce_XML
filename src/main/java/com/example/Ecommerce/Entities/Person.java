package com.example.Ecommerce.Entities;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlRootElement(name = "person")
@XmlAccessorType(XmlAccessType.FIELD)
public class Person {
    @XmlElement
    private String id;

    @XmlElement
    private String firstName;

    @XmlElement
    private String lastName;

    @XmlElement
    private String email;

    @XmlElement
    private String phone;

    @XmlElement
    private String address;

    @XmlElement
    private String role;
}
