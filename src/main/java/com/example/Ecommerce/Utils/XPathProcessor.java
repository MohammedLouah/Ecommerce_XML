package com.example.Ecommerce.Utils;

import javax.xml.xpath.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.Ecommerce.Entities.Product;

@Data
@AllArgsConstructor

@Component
public class XPathProcessor {
    private final XPath xpath;
    private final DocumentBuilder documentBuilder;

    public XPathProcessor() {
        XPathFactory xPathFactory = XPathFactory.newInstance();
        this.xpath = xPathFactory.newXPath();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        try {
            this.documentBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("Failed to initialize XPath processor", e);
        }
    }

    public List<Product> executeProductQuery(String xpathExpression) throws Exception {
        Document document = documentBuilder.parse("src/main/resources/Data/xml/Products.xml");
        NodeList nodes = (NodeList) xpath.evaluate(xpathExpression, document, XPathConstants.NODESET);
        return convertNodesToProducts(nodes);
    }

    public Optional<Product> executeSingleProductQuery(String xpathExpression) throws Exception {
        Document document = documentBuilder.parse("src/main/resources/Data/xml/Products.xml");
        NodeList nodes = (NodeList) xpath.evaluate(xpathExpression, document, XPathConstants.NODESET);

        if (nodes.getLength() == 0) {
            return Optional.empty();
        }

        Element element = (Element) nodes.item(0);
        Product product = new Product();
        product.setId(getElementTextContent(element, "id"));
        product.setName(getElementTextContent(element, "name"));
        product.setBrand(getElementTextContent(element, "brand"));
        product.setCategoryId(getElementTextContent(element, "categoryId"));
        product.setPrice(Double.parseDouble(getElementTextContent(element, "price")));
        product.setSpecifications(getElementTextContent(element, "specifications"));
        product.setStock(Integer.parseInt(getElementTextContent(element, "stock")));

        return Optional.of(product);
    }

    private List<Product> convertNodesToProducts(NodeList nodes) {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
            Product product = new Product();
            product.setId(getElementTextContent(element, "id"));
            product.setName(getElementTextContent(element, "name"));
            product.setBrand(getElementTextContent(element, "brand"));
            product.setCategoryId(getElementTextContent(element, "categoryId"));
            product.setPrice(Double.parseDouble(getElementTextContent(element, "price")));
            product.setSpecifications(getElementTextContent(element, "specifications"));
            product.setStock(Integer.parseInt(getElementTextContent(element, "stock")));
            products.add(product);
        }
        return products;
    }

    private String getElementTextContent(Element parent, String elementName) {
        NodeList nodeList = parent.getElementsByTagName(elementName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }
}
