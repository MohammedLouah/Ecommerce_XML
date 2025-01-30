package com.example.Ecommerce;

import com.example.Ecommerce.Entities.Product;
import com.example.Ecommerce.Services.ProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.xml.bind.JAXBException;
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

}
