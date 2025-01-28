package com.example.Ecommerce.Services;

import com.example.Ecommerce.Entities.Product;
import com.example.Ecommerce.Exceptions.ProductAlreadyExistsException;
import com.example.Ecommerce.Exceptions.ValidationException;
import com.example.Ecommerce.Repositories.ProductRepository;
import org.springframework.stereotype.Service;
import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
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
}