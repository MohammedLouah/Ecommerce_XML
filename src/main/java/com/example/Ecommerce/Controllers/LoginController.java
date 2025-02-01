package com.example.Ecommerce.Controllers;


import com.example.Ecommerce.Entities.Person;
import com.example.Ecommerce.Services.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.bind.JAXBException;
import java.util.Optional;

@Controller
public class LoginController {



    private final PersonService personService;

    public LoginController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Affiche la page de connexion
    }

    @PostMapping("/login")
    public String authenticate(@RequestParam String email, @RequestParam String password, Model model) throws JAXBException {
        Optional<Person> person = personService.authenticate(email, password);
        if (person.isPresent()) {
            model.addAttribute("person", person.get());
            return "welcome"; // Redirige vers la page de bienvenue si authentifié
        } else {
            model.addAttribute("error", "Email ou mot de passe incorrect !");
            return "login"; // Reste sur la page de connexion en cas d'erreur
        }
    }
}
