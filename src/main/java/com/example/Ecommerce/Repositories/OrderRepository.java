package com.example.Ecommerce.Repositories;

import com.example.Ecommerce.Entities.Order;
import com.example.Ecommerce.Entities.OrderLine;
import com.example.Ecommerce.Utils.XPathProcessor;
import com.example.Ecommerce.Utils.XmlValidator;
import com.example.Ecommerce.Wrappers.Orders;
import com.example.Ecommerce.Exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.*;

@Repository
public class OrderRepository extends AbstractXmlRepository<Order, Orders> {
    private static final String XML_FILE_PATH = "src/main/resources/Data/xml/Orders.xml";

    private final XmlValidator xmlValidator;
    private final XPathProcessor xPathProcessor;

    @Autowired
    public OrderRepository(XmlValidator xmlValidator, XPathProcessor xPathProcessor) throws JAXBException {
        super(Orders.class, XML_FILE_PATH);
        this.xmlValidator = xmlValidator;
        this.xPathProcessor = xPathProcessor;
    }

    @Override
    protected void createEmptyXmlStructure() throws JAXBException {
        Orders emptyOrders = new Orders();
        emptyOrders.setOrders(new ArrayList<>());
        writeXml(emptyOrders);
    }

    public List<Order> findAll() throws JAXBException {
        try {
            return xPathProcessor.executeOrderQuery("//order");
        } catch (Exception e) {
            throw new JAXBException("Failed to find all orders", e);
        }
    }

    public Optional<Order> findById(String id) throws JAXBException {
        try {
            String xpathExpression = String.format("//order[id='%s']", id);
            return xPathProcessor.executeSingleOrderQuery(xpathExpression);
        } catch (Exception e) {
            throw new JAXBException("Failed to find order by ID", e);
        }
    }

    public void save(Order order) throws JAXBException, ValidationException {
        try {
            xmlValidator.validateOrder(order);

            if (order.getId() == null) {
                order.setId(UUID.randomUUID().toString());
            }

            Orders orders = readXml();
            orders.getOrders().add(order);
            validateAndWrite(orders);
        } catch (Exception e) {
            e.printStackTrace();
            throw new JAXBException("Failed to save order", e);
        }
    }

    public void update(Order updatedOrder) throws JAXBException, ValidationException {
        try {
            xmlValidator.validateOrder(updatedOrder);

            String othersXpath = String.format("//order[id!='%s']", updatedOrder.getId());
            List<Order> otherOrders = xPathProcessor.executeOrderQuery(othersXpath);

            Orders orders = new Orders();
            otherOrders.add(updatedOrder);
            orders.setOrders(otherOrders);

            validateAndWrite(orders);
        } catch (Exception e) {
            throw new JAXBException("Failed to update order", e);
        }
    }

    public void deleteById(String id) throws JAXBException {
        try {
            String othersXpath = String.format("//order[id!='%s']", id);
            List<Order> remainingOrders = xPathProcessor.executeOrderQuery(othersXpath);

            Orders orders = new Orders();
            orders.setOrders(remainingOrders);
            System.out.println("Before writing XML: " + orders);
            validateAndWrite(orders);
            System.out.println("After writing XML");
        } catch (Exception e) {
            throw new JAXBException("Failed to delete order", e);
        }
    }

    public List<Order> findByClientId(String clientId) throws JAXBException {
        try {
            String xpathExpression = String.format("//order[clientId='%s']", clientId);
            return xPathProcessor.executeOrderQuery(xpathExpression);
        } catch (Exception e) {
            throw new JAXBException("Failed to find orders by client ID", e);
        }
    }

    private void validateAndWrite(Orders orders) throws Exception {
        xmlValidator.validateXmlFile(new File(XML_FILE_PATH),"order");
        writeXml(orders);
    }
}