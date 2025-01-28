package com.example.Ecommerce.Wrappers;

import com.example.Ecommerce.Entities.Category;
import lombok.Data;
import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class Categories {
    @XmlElement(name = "category")
    private List<Category> categories;
}
