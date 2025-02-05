package fr.npagnucco.projetspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controleur pour la page d'accueil
 * @author nathan
 */
@Controller
public class HomeController {
    @GetMapping("/")
    public String home()
    {
        return "home";
    }
}
