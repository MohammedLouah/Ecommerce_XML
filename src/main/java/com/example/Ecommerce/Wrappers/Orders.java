package com.example.Ecommerce.Wrappers;

import com.example.Ecommerce.Entities.Order;
import lombok.Data;
import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlRootElement(name = "orders")
@XmlAccessorType(XmlAccessType.FIELD)
public class Orders {
    @XmlElement(name = "order")
    private List<Order> orders;
}