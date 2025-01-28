package com.example.Ecommerce.Utils;

import com.example.Ecommerce.Entities.Product;
import org.apache.fop.fo.ValidationException;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;

@Component
public class XmlValidator {
    private final Schema productSchema;
    private static final String SCHEMA_PATH = "src/main/resources/Data/xsd/Products.xsd";

    public XmlValidator() {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            this.productSchema = factory.newSchema(new File(SCHEMA_PATH));
        } catch (SAXException e) {
            throw new RuntimeException("Failed to load schema: " + SCHEMA_PATH, e);
        }
    }

    public void validateXmlFile(File xmlFile) throws Exception {
        Validator validator = productSchema.newValidator();
        validator.validate(new StreamSource(xmlFile));
    }

    public void validateProduct(Product product) throws ValidationException {
        validateRequiredField("id", product.getId());
        validatePositiveNumber("price", product.getPrice());
        validateNonNegativeNumber("stock", product.getStock());
        validateRequiredField("name", product.getName());
        validateRequiredField("categoryId", product.getCategoryId());
    }

    private void validateRequiredField(String fieldName, String value) throws ValidationException {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(fieldName + " cannot be empty");
        }
    }

    private void validatePositiveNumber(String fieldName, double value) throws ValidationException {
        if (value <= 0) {
            throw new ValidationException(fieldName + " must be positive");
        }
    }

    private void validateNonNegativeNumber(String fieldName, int value) throws ValidationException {
        if (value < 0) {
            throw new ValidationException(fieldName + " cannot be negative");
        }
    }
}

