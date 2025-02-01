package com.example.Ecommerce;

import com.example.Ecommerce.Entities.Invoice;
import com.example.Ecommerce.Entities.Order;
import com.example.Ecommerce.Entities.OrderLine;
import com.example.Ecommerce.Entities.Product;
import com.example.Ecommerce.Services.InvoiceService;
import com.example.Ecommerce.Services.OrderService;
import com.example.Ecommerce.Services.ProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.xml.bind.JAXBException;
import javax.xml.bind.ValidationException;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

		InvoiceService invoiceService = context.getBean(InvoiceService.class);
		testInvoiceService(invoiceService);

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

		// 7. Ajouter une ligne de commande à une commande existante
		/*System.out.println("\nAjout d'une nouvelle ligne de commande...");
		OrderLine newOrderLine = new OrderLine();
		newOrderLine.setProductId("P002");
		newOrderLine.setQuantity(3);
		newOrderLine.setDiscount(10.0);

		try {
			OrderLine addedLine = orderService.addOrderLine("ORD123", newOrderLine);
			System.out.println("Ligne de commande ajoutée avec succès : " + addedLine);
		} catch (Exception e) {
			System.err.println("Erreur lors de l'ajout de la ligne de commande : " + e.getMessage());
		}

		// 8. Rechercher une ligne de commande par ID
		System.out.println("\nRecherche d'une ligne de commande par ID :");
		Optional<OrderLine> foundOrderLine = orderService.findOrderLineById("ORD123", "OL001");
		if (foundOrderLine.isPresent()) {
			System.out.println("Ligne de commande trouvée : " + foundOrderLine.get());
		} else {
			System.out.println("Aucune ligne de commande trouvée avec cet ID.");
		}

		// 9. Mettre à jour une ligne de commande
		System.out.println("\nMise à jour d'une ligne de commande...");
		foundOrderLine.ifPresent(line -> {
			try {
				line.setQuantity(5);
				line.setDiscount(15.0);
				orderService.updateOrderLine("ORD123", line);
				System.out.println("Ligne de commande mise à jour avec succès : " + line);
			} catch (Exception e) {
				System.err.println("Erreur lors de la mise à jour de la ligne de commande : " + e.getMessage());
			}
		});

		// 10. Rechercher toutes les lignes de commande pour un produit
		System.out.println("\nRecherche des lignes de commande pour le produit P001 :");
		try {
			List<OrderLine> productOrderLines = orderService.findOrderLinesByProductId("P001");
			if (!productOrderLines.isEmpty()) {
				System.out.println("Lignes de commande trouvées pour le produit P001 :");
				productOrderLines.forEach(System.out::println);
			} else {
				System.out.println("Aucune ligne de commande trouvée pour ce produit.");
			}
		} catch (Exception e) {
			System.err.println("Erreur lors de la recherche des lignes de commande : " + e.getMessage());
		}

		// 11. Supprimer une ligne de commande
		System.out.println("\nSuppression d'une ligne de commande...");
		try {
			orderService.deleteOrderLine("ORD123", "OL001");
			System.out.println("Ligne de commande supprimée avec succès.");
		} catch (Exception e) {
			System.err.println("Erreur lors de la suppression de la ligne de commande : " + e.getMessage());
		}

		*/


		// Tests d'export (commentés par défaut comme dans l'exemple)

    //System.out.println("HTML: " + orderService.exportOrdersToHtml());
    //System.out.println("PDF: " + orderService.exportOrdersToPdf());

	}

	private static void testInvoiceService(InvoiceService invoiceService) throws Exception {
		// 1. Afficher toutes les factures
		/*System.out.println("Liste des factures :");
		List<Invoice> invoices = invoiceService.findAll();
		invoices.forEach(System.out::println);*/

		// 2. Ajouter une nouvelle facture
		/*System.out.println("\nAjout d'une nouvelle facture...");
		Invoice newInvoice = new Invoice();
		newInvoice.setId("INV123");
		newInvoice.setOrderId("ORD123");
		newInvoice.setDate("2025-01-31");
		newInvoice.setStatus("pending");
		System.out.println(newInvoice);

		try {
			Invoice createdInvoice = invoiceService.createInvoice(newInvoice);
			System.out.println("Facture ajoutée avec succès : " + createdInvoice);
		} catch (JAXBException e) {
			System.err.println("Erreur lors de l'ajout de la facture : " + e.getMessage());
		}*/

		// 3. Rechercher une facture par ID
		/*System.out.println("\nRecherche d'une facture par ID :");
		Optional<Invoice> foundInvoice = invoiceService.findById("INV123");
		foundInvoice.ifPresentOrElse(
				invoice -> System.out.println("Facture trouvée : " + invoice),
				() -> System.out.println("Aucune facture trouvée avec cet ID.")
		);*/

		// 4. Rechercher les factures d'une commande
		/*System.out.println("\nRecherche des factures de la commande ORD123 :");
		List<Invoice> orderInvoices = invoiceService.findByOrderId("ORD123");
		if (orderInvoices.isEmpty()) {
			System.out.println("Aucune facture trouvée pour cette commande.");
		} else {
			orderInvoices.forEach(System.out::println);
		}*/

		// 5. Mettre à jour le statut d'une facture
		/*System.out.println("\nMise à jour du statut d'une facture...");
		try {
			invoiceService.updateInvoiceStatus("INV123", "paid");
			System.out.println("Statut de la facture mis à jour avec succès");
		} catch (Exception e) {
			System.err.println("Erreur lors de la mise à jour du statut : " + e.getMessage());
		}*/

		// 6. Mettre à jour une facture complète
		/*Optional<Invoice> foundInvoice;
		foundInvoice = invoiceService.findById("INV123");
		System.out.println("\nMise à jour d'une facture...");
		if (foundInvoice.isPresent()) {
			Invoice invoice = foundInvoice.get();
			invoice.setStatus("cancelled");
			invoice.setDate("2025-02-01");
			try {
				invoiceService.updateInvoice(invoice);
				System.out.println("Facture mise à jour avec succès : " + invoice);
			} catch (JAXBException e) {
				System.err.println("Erreur lors de la mise à jour de la facture : " + e.getMessage());
			}
		}*/

		// 7. Test des exports
		/*System.out.println("\nTest des exports...");
		try {
			// Export de toutes les factures
			/*File htmlFile = invoiceService.exportInvoicesToHtml();
			System.out.println("Export HTML de toutes les factures : " + htmlFile.getAbsolutePath());

			File pdfFile = invoiceService.exportInvoicesToPdf();
			System.out.println("Export PDF de toutes les factures : " + pdfFile.getAbsolutePath());*/

			// Export d'une facture spécifique
			/*File singleHtmlFile = invoiceService.exportInvoiceToHtml("INV001");
			System.out.println("Export HTML d'une facture spécifique : " + singleHtmlFile.getAbsolutePath());

			File singlePdfFile = invoiceService.exportInvoiceToPdf("INV001");
			System.out.println("Export PDF d'une facture spécifique : " + singlePdfFile.getAbsolutePath());
		} catch (Exception e) {
			System.err.println("Erreur lors de l'export : " + e.getMessage());
		}*/

		// 8. Supprimer une facture
		/*System.out.println("\nSuppression d'une facture...");
		try {
			invoiceService.deleteInvoice("INV123");
			System.out.println("Facture supprimée avec succès.");
		} catch (JAXBException e) {
			System.err.println("Erreur lors de la suppression de la facture : " + e.getMessage());
		}*/
	}
}
