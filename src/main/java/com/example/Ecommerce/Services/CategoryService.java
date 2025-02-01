package com.example.Ecommerce.Services;

import com.example.Ecommerce.Entities.Category;
import com.example.Ecommerce.Repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() throws JAXBException {
        return categoryRepository.findAll();
    }


    // Additional methods for updating or deleting categories can be added here
}