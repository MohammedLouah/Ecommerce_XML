package com.example.Ecommerce.Controllers;


import com.example.Ecommerce.Services.ProductService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.nio.file.Files;

@Controller
public class ViewController {
    private final ProductService productService;

    public ViewController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String showIndex(Model model) throws Exception {
        model.addAttribute("products", productService.findAll());
        return "index";  // Charge le fichier "index.html" depuis templates/
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportProducts(@RequestParam String format) throws Exception {
        File file;
        String contentType;
        String fileName;

        if ("html".equalsIgnoreCase(format)) {
            file = productService.exportProductsToHtml();
            contentType = "text/html";
            fileName = "products.html";
        } else if ("pdf".equalsIgnoreCase(format)) {
            file = productService.exportProductsToPdf();
            contentType = "application/pdf";
            fileName = "products.pdf";
        } else {
            return ResponseEntity.badRequest().build();
        }

        byte[] fileContent = Files.readAllBytes(file.toPath());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(fileContent);
    }

}

