package com.example.Ecommerce.Services;

import com.example.Ecommerce.Entities.Product;
import com.example.Ecommerce.Exceptions.ProductAlreadyExistsException;
import com.example.Ecommerce.Exceptions.ValidationException;
import com.example.Ecommerce.Repositories.ProductRepository;
import com.example.Ecommerce.Utils.XsltTransformer;
import org.springframework.stereotype.Service;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final XsltTransformer xsltTransformer;

    public ProductService(ProductRepository productRepository, XsltTransformer xsltTransformer) {
        this.productRepository = productRepository;
        this.xsltTransformer = xsltTransformer;
    }

    public List<Product> findAll() throws JAXBException {
        return productRepository.findAll();
    }

    public Optional<Product> findById(String id) throws JAXBException {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) throws JAXBException, ProductAlreadyExistsException, ValidationException {
        productRepository.save(product);
        return product;
    }

    public void updateProduct(Product product) throws JAXBException, ValidationException {
        productRepository.update(product);
    }

    public void deleteProduct(String id) throws JAXBException {
        productRepository.deleteById(id);
    }

    public void updateStock(String id, int quantity) throws JAXBException, ValidationException {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setStock(product.getStock() + quantity);
            productRepository.update(product);
        }
    }

    public File exportProductsToHtml() throws Exception {
        // Chemin du fichier XML source
        File xmlSource = new File("src/main/resources/Data/xml/Products.xml");

        // Nom du template XSLT à utiliser
        String xsltTemplate = "products"; // Assure-toi que le fichier "products-to-html.xsl" existe dans le répertoire XSLT

        // Transformation en fichier HTML
        return xsltTransformer.generateHtml(xmlSource, xsltTemplate);
    }

    public File exportProductsToPdf() throws Exception {
        // Chemin du fichier XML source
        File xmlSource = new File("src/main/resources/Data/xml/Products.xml");

        // Nom du template XSLT-FO à utiliser
        String xsltTemplate = "products"; // Assure-toi que le fichier "products-to-fo.xsl" existe dans le répertoire XSLT

        // Transformation en fichier PDF
        return xsltTransformer.generatePdf(xmlSource, xsltTemplate);
    }

}