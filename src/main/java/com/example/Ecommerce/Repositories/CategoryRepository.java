package com.example.Ecommerce.Repositories;

import com.example.Ecommerce.Entities.Category;
import com.example.Ecommerce.Wrappers.Categories;
import com.example.Ecommerce.Utils.XPathProcessor;
import com.example.Ecommerce.Utils.XmlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.*;

@Repository
public class CategoryRepository extends AbstractXmlRepository<Category, Categories> {
    private static final String XML_FILE_PATH = "src/main/resources/Data/xml/Categories.xml";

    private final XmlValidator xmlValidator;
    private final XPathProcessor xPathProcessor;

    @Autowired
    public CategoryRepository(XmlValidator xmlValidator, XPathProcessor xPathProcessor) throws JAXBException {
        super(Categories.class, XML_FILE_PATH);
        this.xmlValidator = xmlValidator;
        this.xPathProcessor = xPathProcessor;
    }

    @Override
    protected void createEmptyXmlStructure() throws JAXBException {
        Categories emptyCategories = new Categories();
        emptyCategories.setCategories(new ArrayList<>());
        writeXml(emptyCategories);
    }

    public List<Category> findAll() throws JAXBException {
        return readXml().getCategories(); // Reads the categories from XML
    }


}