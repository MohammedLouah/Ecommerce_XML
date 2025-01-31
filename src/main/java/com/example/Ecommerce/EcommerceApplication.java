package com.example.Ecommerce;

import com.example.Ecommerce.Entities.Order;
import com.example.Ecommerce.Entities.OrderLine;
import com.example.Ecommerce.Entities.Product;
import com.example.Ecommerce.Services.OrderService;
import com.example.Ecommerce.Services.ProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) throws Exception {
		// Démarrage de l'application Spring Boot
		ApplicationContext context = SpringApplication.run(EcommerceApplication.class, args);

		// Récupérer le bean ProductService
		ProductService productService = context.getBean(ProductService.class);

		// Tester les méthodes du service
		testProductService(productService);

		OrderService orderService = context.getBean(OrderService.class);
		testOrderService(orderService);


	}
	private static void testProductService(ProductService productService) throws Exception {
		/*// 1. Afficher tous les produits
		System.out.println("Liste des produits :");
		List<Product> products = productService.findAll();
		products.forEach(System.out::println);

		// 2. Ajouter un nouveau produit
		System.out.println("\nAjout d'un nouveau produit...");
		Product newProduct = new Product();
		newProduct.setId("P090");
		newProduct.setName("Sumsung A10S");
		newProduct.setBrand("Sumsung");
		newProduct.setCategoryId("CAT003");
		newProduct.setPrice(1700);
		newProduct.setSpecifications("8GB RAM, 256GB SSD");
		newProduct.setStock(10);

		try {
			productService.createProduct(newProduct);
			System.out.println("Produit ajouté avec succès : " + newProduct);
		} catch (Exception e) {
			System.err.println("Erreur lors de l'ajout du produit : " + e.getMessage());
		}

		System.out.println("Liste des produits :");

		products.forEach(System.out::println);*/

		/*// 3. Rechercher un produit par ID
		System.out.println("\nRecherche d'un produit par ID :");
		Product foundProduct = productService.findById("P001").orElse(null);
		if (foundProduct != null) {
			System.out.println("Produit trouvé : " + foundProduct);
		} else {
			System.out.println("Aucun produit trouvé avec cet ID.");
		}

		// 4. Mettre à jour un produit
		System.out.println("\nMise à jour d'un produit...");
		if (foundProduct != null) {
			foundProduct.setPrice(120.0);
			productService.updateProduct(foundProduct);
			System.out.println("Produit mis à jour avec succès : " + foundProduct);
		}

		// 5. Supprimer un produit
		System.out.println("\nSuppression d'un produit...");
		productService.deleteProduct("P001");
		System.out.println("Produit supprimé avec succès.");

		*/

		//System.out.println("HTML: "+productService.exportProductsToHtml());
		//System.out.println("PDF: "+productService.exportProductsToPdf());
	}

	private static void testOrderService(OrderService orderService) throws Exception {
		// Autoriser l'accès aux DTD externes
		/*System.setProperty("javax.xml.accessExternalDTD", "all");
		System.setProperty("javax.xml.accessExternalSchema", "all");*/

		// 1. Afficher toutes les commandes
		/*System.out.println("Liste des commandes :");
		List<Order> orders = orderService.findAll();
		orders.forEach(System.out::println);*/

		// 2. Ajouter une nouvelle commande
		/*System.out.println("\nAjout d'une nouvelle commande...");
		Order newOrder = new Order();
		newOrder.setId("ORD123");
		newOrder.setClientId("CLI001");
		newOrder.setDate("2025-01-26");
		newOrder.setStatus("En attente");

		// Création des lignes de commande
		List<OrderLine> orderLines = new ArrayList<>();
		OrderLine line1 = new OrderLine();
		line1.setId("OL001");
		line1.setProductId("P001");
		line1.setQuantity(2);
		line1.setDiscount(20);
		orderLines.add(line1);

		newOrder.setOrderLines(orderLines);

		try {
			orderService.createOrder(newOrder);
			System.out.println("Commande ajoutée avec succès : " + newOrder);
		} catch (Exception e) {
			System.err.println("Erreur lors de l'ajout de la commande : " + e.getMessage());
		}*/

		// 3. Rechercher une commande par ID
		/*System.out.println("\nRecherche d'une commande par ID :");
		Order foundOrder = orderService.findById("ORD123").orElse(null);
		if (foundOrder != null) {
			System.out.println("Commande trouvée : " + foundOrder);
		} else {
			System.out.println("Aucune commande trouvée avec cet ID.");
		}*/

		// 4. Rechercher les commandes d'un client
		/*System.out.println("\nRecherche des commandes du client CLI001 :");
		List<Order> clientOrders = orderService.findByClientId("CLI001");
		clientOrders.forEach(System.out::println);*/

		// 5. Mettre à jour une commande
		/*Order foundOrder = orderService.findById("ORD123").orElse(null);
		System.out.println("\nMise à jour d'une commande...");
		if (foundOrder != null) {
			foundOrder.setStatus("En cours de traitement");
			orderService.updateOrder(foundOrder);
			System.out.println("Commande mise à jour avec succès : " + foundOrder);
		}*/

		// 6. Supprimer une commande
		/*
		System.out.println("\nSuppression d'une commande...");
		orderService.deleteOrder("ORD123");
		System.out.println("Commande supprimée avec succès.");*/

		// Tests d'export (commentés par défaut comme dans l'exemple)

    //System.out.println("HTML: " + orderService.exportOrdersToHtml());
    //System.out.println("PDF: " + orderService.exportOrdersToPdf());

	}
}
