package com.example.Ecommerce.Utils;

import com.example.Ecommerce.Entities.Order;
import com.example.Ecommerce.Entities.Product;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.ValidationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Component
public class XmlValidator {
    private final Map<String, Schema> schemas;
    private static final String XSD_BASE_PATH = "src/main/resources/Data/xsd/";

    public XmlValidator() {
        schemas = new HashMap<>();
        loadSchemas();
    }

    private void loadSchemas() {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            // Chargement des schémas
            schemas.put("product", factory.newSchema(new File(XSD_BASE_PATH + "Products.xsd")));
            schemas.put("order", factory.newSchema(new File(XSD_BASE_PATH + "Orders.xsd")));
            // Ajoutez d'autres schémas si nécessaire
        } catch (SAXException e) {
            throw new RuntimeException("Failed to load schemas", e);
        }
    }

    public void validateXmlFile(File xmlFile, String type) throws Exception {
        Schema schema = schemas.get(type.toLowerCase());
        if (schema == null) {
            throw new IllegalArgumentException("Schema not found for type: " + type);
        }
        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(xmlFile));
    }

    // Méthodes de validation existantes pour Product
    public void validateProduct(Product product) throws ValidationException {
        validateRequiredField("id", product.getId());
        validatePositiveNumber("price", product.getPrice());
        validateNonNegativeNumber("stock", product.getStock());
        validateRequiredField("name", product.getName());
        validateRequiredField("categoryId", product.getCategoryId());
    }

    // Méthodes de validation existantes pour Order
    public void validateOrder(Order order) throws ValidationException {
        validateRequiredField("id", order.getId());
        validateRequiredField("clientId", order.getClientId());
        validateRequiredField("date", order.getDate());
        validateRequiredField("status", order.getStatus());
        if (order.getOrderLines() == null || order.getOrderLines().isEmpty()) {
            throw new ValidationException("Order lines are required");
        }
    }

    // Méthodes utilitaires de validation
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