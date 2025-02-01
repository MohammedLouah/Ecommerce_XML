package com.example.Ecommerce.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class dashbord {



    @GetMapping("/le")
    public String loginPage() {
        return "dashbord"; // Affiche la page de connexion
    }
}
