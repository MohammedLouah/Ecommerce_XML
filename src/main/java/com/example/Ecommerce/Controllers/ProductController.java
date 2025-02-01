package com.example.Ecommerce.Controllers;

import com.example.Ecommerce.Entities.Product;
import com.example.Ecommerce.Services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.Optional;


@Controller
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String listProducts(Model model) {
        try {
            List<Product> products = productService.findAll();
            model.addAttribute("products", products);
        } catch (JAXBException e) {
            model.addAttribute("error", "Failed to load products.");
        }
        return "products"; // Nom de la vue
    }


    @GetMapping("/pp")
    public String listProductsclient(Model model) {
        try {
            List<Product> products = productService.findAll();
            model.addAttribute("products", products);
        } catch (JAXBException e) {
            model.addAttribute("error", "Failed to load products.");
        }
        return "ProductC"; // Nom de la vue
    }



    // ProductController.java
    @GetMapping("/product/details/{id}")
    public String viewProductDetails(@PathVariable String id, Model model) {
        try {
            Optional<Product> product = productService.findById(id);
            if (product.isPresent()) {
                model.addAttribute("product", product.get());
                return "productDetails"; // Nom de la vue pour les détails du produit
            } else {
                model.addAttribute("error", "Produit non trouvé.");
            }
        } catch (JAXBException e) {
            model.addAttribute("error", "Échec du chargement des détails du produit.");
        }
        return "products"; // Redirige vers la liste des produits en cas d'erreur
    }




    @GetMapping("/productC/client/{id}")
    public String viewProductDetailsC(@PathVariable String id, Model model) {
        try {
            Optional<Product> product = productService.findById(id);
            if (product.isPresent()) {
                model.addAttribute("product", product.get());
                return "ProductDetailC"; // Nom de la vue pour les détails du produit
            } else {
                model.addAttribute("error", "Produit non trouvé.");
            }
        } catch (JAXBException e) {
            model.addAttribute("error", "Échec du chargement des détails du produit.");
        }
        return "products"; // Redirige vers la liste des produits en cas d'erreur
    }




}
