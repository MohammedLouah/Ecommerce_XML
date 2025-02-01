package com.example.Ecommerce.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class registerController {



    @GetMapping("/register")
    public String loginPage() {
        return "register"; // Affiche la page de connexion
    }

}
