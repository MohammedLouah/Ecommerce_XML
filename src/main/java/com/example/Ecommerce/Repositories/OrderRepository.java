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

    // Méthodes pour gérer les OrderLines
    public Optional<OrderLine> findOrderLineById(String orderId, String orderLineId) throws JAXBException {
        try {
            String xpathExpression = String.format("//order[id='%s']/orderLines/orderLine[id='%s']", orderId, orderLineId);
            return xPathProcessor.executeSingleOrderLineQuery(xpathExpression);
        } catch (Exception e) {
            throw new JAXBException("Failed to find order line", e);
        }
    }

    public void addOrderLine(String orderId, OrderLine orderLine) throws JAXBException, ValidationException {
        try {
            Optional<Order> orderOpt = findById(orderId);
            if (!orderOpt.isPresent()) {
                throw new JAXBException("Order not found with ID: " + orderId);
            }

            Order order = orderOpt.get();
            validateOrderLine(orderLine);

            if (orderLine.getId() == null) {
                orderLine.setId(UUID.randomUUID().toString());
            }

            order.addOrderLine(orderLine);
            update(order);
        } catch (Exception e) {
            throw new JAXBException("Failed to add order line", e);
        }
    }

    public void updateOrderLine(String orderId, OrderLine updatedOrderLine) throws JAXBException, ValidationException {
        try {
            Optional<Order> orderOpt = findById(orderId);
            if (!orderOpt.isPresent()) {
                throw new JAXBException("Order not found with ID: " + orderId);
            }

            Order order = orderOpt.get();
            validateOrderLine(updatedOrderLine);

            boolean found = false;
            for (int i = 0; i < order.getOrderLines().size(); i++) {
                if (order.getOrderLines().get(i).getId().equals(updatedOrderLine.getId())) {
                    order.getOrderLines().set(i, updatedOrderLine);
                    found = true;
                    break;
                }
            }

            if (!found) {
                throw new JAXBException("Order line not found with ID: " + updatedOrderLine.getId());
            }

            update(order);
        } catch (Exception e) {
            throw new JAXBException("Failed to update order line", e);
        }
    }

    public void deleteOrderLine(String orderId, String orderLineId) throws JAXBException {
        try {
            Optional<Order> orderOpt = findById(orderId);
            if (!orderOpt.isPresent()) {
                throw new JAXBException("Order not found with ID: " + orderId);
            }

            Order order = orderOpt.get();
            boolean removed = order.getOrderLines().removeIf(line -> line.getId().equals(orderLineId));

            if (!removed) {
                throw new JAXBException("Order line not found with ID: " + orderLineId);
            }

            update(order);
        } catch (Exception e) {
            throw new JAXBException("Failed to delete order line", e);
        }
    }

    public List<OrderLine> findOrderLinesByProductId(String productId) throws JAXBException {
        try {
            String xpathExpression = String.format("//orderLine[productId='%s']", productId);
            return xPathProcessor.executeOrderLineQuery(xpathExpression);
        } catch (Exception e) {
            throw new JAXBException("Failed to find order lines by product ID", e);
        }
    }

    private void validateOrderLine(OrderLine orderLine) throws ValidationException {
        if (orderLine.getProductId() == null || orderLine.getProductId().trim().isEmpty()) {
            throw new ValidationException("Product ID cannot be empty");
        }
        if (orderLine.getQuantity() <= 0) {
            throw new ValidationException("Quantity must be positive");
        }
        if (orderLine.getDiscount() < 0 || orderLine.getDiscount() > 100) {
            throw new ValidationException("Discount must be between 0 and 100");
        }
    }
}