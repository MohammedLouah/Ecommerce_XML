package com.example.Ecommerce.Wrappers;

import com.example.Ecommerce.Entities.Invoice;
import lombok.Data;
import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlRootElement(name = "invoices")
@XmlAccessorType(XmlAccessType.FIELD)
public class Invoices {
    @XmlElement(name = "invoice")
    private List<Invoice> invoices;
}
