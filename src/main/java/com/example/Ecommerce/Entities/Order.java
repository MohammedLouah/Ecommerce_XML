package com.example.Ecommerce.Entities;

import lombok.Data;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Data
@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.FIELD)
public class Order {
    @XmlElement
    private String id;

    @XmlElement
    private String clientId;

    @XmlElement
    private String date;

    @XmlElement
    private String status;

    @XmlElementWrapper(name = "orderLines")
    @XmlElement(name = "orderLine")
    private List<OrderLine> orderLines;

    public void addOrderLine(OrderLine orderLine) {
        if (this.orderLines == null) {
            this.orderLines = new ArrayList<>();
        }

        // Éviter les doublons basés sur l'ID
        if (orderLine.getId() != null) {
            this.orderLines.removeIf(line -> line.getId().equals(orderLine.getId()));
        }

        this.orderLines.add(orderLine);
    }
}
