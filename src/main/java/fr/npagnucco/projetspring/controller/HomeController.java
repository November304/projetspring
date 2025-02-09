package fr.npagnucco.projetspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fr.npagnucco.projetspring.repository.MenuRepository;
import fr.npagnucco.projetspring.repository.PlatRepository;

/**
 * Controleur pour la page d'accueil
 * @author nathan
 */
@Controller
public class HomeController {
    private final PlatRepository repoPlat;
    private final MenuRepository repoMenu;

    public HomeController(PlatRepository platRepository, MenuRepository menuRepository) {
        this.repoPlat = platRepository;
        this.repoMenu = menuRepository;
    }

    @GetMapping("/")
    public String home(Model model)
    {
        model.addAttribute("nbPlats", repoPlat.count());
        model.addAttribute("nbMenus", repoMenu.count());
        return "home";
    }
}
