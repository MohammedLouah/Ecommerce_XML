package com.example.Ecommerce.Services;

import com.example.Ecommerce.Entities.Order;
import com.example.Ecommerce.Entities.OrderLine;
import com.example.Ecommerce.Exceptions.ValidationException;
import com.example.Ecommerce.Repositories.OrderRepository;
import com.example.Ecommerce.Utils.XsltTransformer;
import org.springframework.stereotype.Service;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final XsltTransformer xsltTransformer;

    public OrderService(OrderRepository orderRepository, XsltTransformer xsltTransformer) {
        this.orderRepository = orderRepository;
        this.xsltTransformer = xsltTransformer;
    }

    public List<Order> findAll() throws JAXBException {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(String id) throws JAXBException {
        return orderRepository.findById(id);
    }

    public List<Order> findByClientId(String clientId) throws JAXBException {
        return orderRepository.findByClientId(clientId);
    }

    public Order createOrder(Order order) throws JAXBException, ValidationException {
        orderRepository.save(order);
        return order;
    }

    public void updateOrder(Order order) throws JAXBException, ValidationException {
        orderRepository.update(order);
    }

    public void deleteOrder(String id) throws JAXBException {
        orderRepository.deleteById(id);
    }

    public File exportOrdersToHtml() throws Exception {
        // Chemin du fichier XML source
        File xmlSource = new File("src/main/resources/Data/xml/Orders.xml");

        // Nom du template XSLT à utiliser
        String xsltTemplate = "orders";

        // Transformation en fichier HTML
        return xsltTransformer.generateHtml(xmlSource, xsltTemplate);
    }

    public File exportOrdersToPdf() throws Exception {
        // Chemin du fichier XML source
        File xmlSource = new File("src/main/resources/Data/xml/Orders.xml");

        // Nom du template XSLT-FO à utiliser
        String xsltTemplate = "orders";

        // Transformation en fichier PDF
        return xsltTransformer.generatePdf(xmlSource, xsltTemplate);
    }

    // New OrderLine methods
    public Optional<OrderLine> findOrderLineById(String orderId, String orderLineId) throws JAXBException {
        return orderRepository.findOrderLineById(orderId, orderLineId);
    }

    public OrderLine addOrderLine(String orderId, OrderLine orderLine) throws JAXBException, ValidationException {
        orderRepository.addOrderLine(orderId, orderLine);
        return orderLine;
    }

    public void updateOrderLine(String orderId, OrderLine orderLine) throws JAXBException, ValidationException {
        orderRepository.updateOrderLine(orderId, orderLine);
    }

    public void deleteOrderLine(String orderId, String orderLineId) throws JAXBException {
        orderRepository.deleteOrderLine(orderId, orderLineId);
    }

    public List<OrderLine> findOrderLinesByProductId(String productId) throws JAXBException {
        return orderRepository.findOrderLinesByProductId(productId);
    }
}
