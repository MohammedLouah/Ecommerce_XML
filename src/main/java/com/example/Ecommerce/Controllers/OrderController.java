package com.example.Ecommerce.Controllers;

import com.example.Ecommerce.Entities.Order;
import com.example.Ecommerce.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.xml.bind.JAXBException;
import java.util.List;

@Controller
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/Orderlist")
    public String listProducts(Model model) {
        try {
            List<Order> orders = orderService.findAll();
            System.out.println("Liste des commandes : " + orders);  // Vérifie les données dans la console
       // Ajoute les commandes au modèle
            model.addAttribute("orders", orders);
        } catch (JAXBException e) {
            model.addAttribute("error", "Failed to load products.");
            e.printStackTrace();  // Affiche l'exception dans la console
        }
        return "Order/OrderList"; // Retourne la vue
    }
}
