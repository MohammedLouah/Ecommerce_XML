package com.example.Ecommerce.Wrappers;

import com.example.Ecommerce.Entities.Person;
import lombok.Data;
import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlRootElement(name = "persons")
@XmlAccessorType(XmlAccessType.FIELD)
public class Persons {
    @XmlElement(name = "person")
    private List<Person> persons;
}
