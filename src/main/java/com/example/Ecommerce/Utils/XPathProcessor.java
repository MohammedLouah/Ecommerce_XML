package com.example.Ecommerce.Utils;

import javax.xml.xpath.*;

import com.example.Ecommerce.Entities.Order;
import com.example.Ecommerce.Entities.OrderLine;
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
        product.setImage(getElementTextContent(element, "image"));
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
            product.setImage(getElementTextContent(element, "image"));
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


    public List<Order> executeOrderQuery(String xpathExpression) throws Exception {
        Document document = documentBuilder.parse("src/main/resources/Data/xml/Orders.xml");
        NodeList nodes = (NodeList) xpath.evaluate(xpathExpression, document, XPathConstants.NODESET);
        return convertNodesToOrders(nodes);
    }

    public Optional<Order> executeSingleOrderQuery(String xpathExpression) throws Exception {
        Document document = documentBuilder.parse("src/main/resources/Data/xml/Orders.xml");
        NodeList nodes = (NodeList) xpath.evaluate(xpathExpression, document, XPathConstants.NODESET);

        if (nodes.getLength() == 0) {
            return Optional.empty();
        }

        Element element = (Element) nodes.item(0);
        Order order = new Order();
        order.setId(getElementTextContent(element, "id"));
        order.setClientId(getElementTextContent(element, "clientId"));
        order.setDate(getElementTextContent(element, "date"));
        order.setStatus(getElementTextContent(element, "status"));

        // Convertir les orderLines
        NodeList orderLineNodes = element.getElementsByTagName("orderLine");
        List<OrderLine> orderLines = new ArrayList<>();
        for (int i = 0; i < orderLineNodes.getLength(); i++) {
            Element orderLineElement = (Element) orderLineNodes.item(i);
            OrderLine orderLine = new OrderLine();
            orderLine.setId(getElementTextContent(orderLineElement, "id"));
            orderLine.setProductId(getElementTextContent(orderLineElement, "productId"));
            orderLine.setQuantity(Integer.parseInt(getElementTextContent(orderLineElement, "quantity")));
            orderLine.setDiscount(Double.parseDouble(getElementTextContent(orderLineElement, "discount")));
            orderLines.add(orderLine);
        }
        order.setOrderLines(orderLines);

        return Optional.of(order);
    }

    private List<Order> convertNodesToOrders(NodeList nodes) {
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
            Order order = new Order();
            order.setId(getElementTextContent(element, "id"));
            order.setClientId(getElementTextContent(element, "clientId"));
            order.setDate(getElementTextContent(element, "date"));
            order.setStatus(getElementTextContent(element, "status"));

            // Convertir les orderLines
            NodeList orderLineNodes = element.getElementsByTagName("orderLine");
            List<OrderLine> orderLines = new ArrayList<>();
            for (int j = 0; j < orderLineNodes.getLength(); j++) {
                Element orderLineElement = (Element) orderLineNodes.item(j);
                OrderLine orderLine = new OrderLine();
                orderLine.setId(getElementTextContent(orderLineElement, "id"));
                orderLine.setProductId(getElementTextContent(orderLineElement, "productId"));
                orderLine.setQuantity(Integer.parseInt(getElementTextContent(orderLineElement, "quantity")));
                orderLine.setDiscount(Double.parseDouble(getElementTextContent(orderLineElement, "discount")));
                orderLines.add(orderLine);
            }
            order.setOrderLines(orderLines);

            orders.add(order);
        }
        return orders;
    }
}
