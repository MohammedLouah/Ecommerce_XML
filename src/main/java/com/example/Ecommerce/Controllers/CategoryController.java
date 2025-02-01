package com.example.Ecommerce.Controllers;

import com.example.Ecommerce.Entities.Category;
import com.example.Ecommerce.Services.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.bind.JAXBException;
import java.util.List;

@Controller
public class CategoryController {


    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public String listCategories(Model model) {
        try {
            List<Category> categories = categoryService.findAll();
            model.addAttribute("categories", categories);
        } catch (JAXBException e) {
            model.addAttribute("error", "Failed to load categories.");
            e.printStackTrace();
        }
        return "CatgoryList"; // The view name
    }






}