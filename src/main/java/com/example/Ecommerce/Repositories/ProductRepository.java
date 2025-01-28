package com.example.Ecommerce.Repositories;

import com.example.Ecommerce.Entities.Product;
import com.example.Ecommerce.Utils.XPathProcessor;
import com.example.Ecommerce.Utils.XmlValidator;
import com.example.Ecommerce.Wrappers.Products;
import com.example.Ecommerce.Exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.*;

@Repository
public class ProductRepository extends AbstractXmlRepository<Product, Products> {
    private static final String XML_FILE_PATH = "src/main/resources/Data/xml/Products.xml";

    private final XmlValidator xmlValidator;
    private final XPathProcessor xPathProcessor;

    @Autowired
    public ProductRepository(XmlValidator xmlValidator, XPathProcessor xPathProcessor) throws JAXBException {
        super(Products.class, XML_FILE_PATH);
        this.xmlValidator = xmlValidator;
        this.xPathProcessor = xPathProcessor;
    }

    @Override
    protected void createEmptyXmlStructure() throws JAXBException {
        Products emptyProducts = new Products();
        emptyProducts.setProducts(new ArrayList<>());
        writeXml(emptyProducts);
    }

    public List<Product> findAll() throws JAXBException {
        try {
            return xPathProcessor.executeProductQuery("//product");
        } catch (Exception e) {
            throw new JAXBException("Failed to find all products", e);
        }
    }

    public Optional<Product> findById(String id) throws JAXBException {
        try {
            String xpathExpression = String.format("//product[id='%s']", id);
            return xPathProcessor.executeSingleProductQuery(xpathExpression);
        } catch (Exception e) {
            throw new JAXBException("Failed to find product by ID", e);
        }
    }

    public void save(Product product) throws JAXBException, ProductAlreadyExistsException, ValidationException {
        try {
            xmlValidator.validateProduct(product);

            if (product.getId() == null) {
                product.setId(UUID.randomUUID().toString());
            }

            Products products = readXml();
            products.getProducts().add(product);
            validateAndWrite(products);
        } catch (Exception e) {
            e.printStackTrace();
            throw new JAXBException("Failed to save product", e);
        }
    }

    public void update(Product updatedProduct) throws JAXBException, ValidationException {
        try {
            xmlValidator.validateProduct(updatedProduct);

            String othersXpath = String.format("//product[id!='%s']", updatedProduct.getId());
            List<Product> otherProducts = xPathProcessor.executeProductQuery(othersXpath);

            Products products = new Products();
            otherProducts.add(updatedProduct);
            products.setProducts(otherProducts);

            validateAndWrite(products);
        } catch (Exception e) {
            throw new JAXBException("Failed to update product", e);
        }
    }

    public void deleteById(String id) throws JAXBException {
        try {
            String othersXpath = String.format("//product[id!='%s']", id);
            List<Product> remainingProducts = xPathProcessor.executeProductQuery(othersXpath);

            Products products = new Products();
            products.setProducts(remainingProducts);
            validateAndWrite(products);
        } catch (Exception e) {
            throw new JAXBException("Failed to delete product", e);
        }
    }

    private void validateAndWrite(Products products) throws Exception {
        xmlValidator.validateXmlFile(new File(XML_FILE_PATH));
        writeXml(products);
    }
}