package com.example.Ecommerce.Entities;

import lombok.Data;
import javax.xml.bind.annotation.*;

@Data
@XmlRootElement(name = "invoice")
@XmlAccessorType(XmlAccessType.FIELD)
public class Invoice {
    @XmlElement
    private String id;

    @XmlElement
    private String orderId;

    @XmlElement
    private String date;

    @XmlElement
    private String status;
}
