package fr.npagnucco.projetspring.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fr.npagnucco.projetspring.model.Menu;
import fr.npagnucco.projetspring.repository.MenuRepository;

/**
 * Controleur pour les CRUD des menus
 * @author nathan
 */
@Controller
public class MenuController {
    private final MenuRepository repoMenu;

    public MenuController(MenuRepository menuRepository) {
        this.repoMenu = menuRepository;
    }

    @GetMapping("/menus")
    public String listeMenus(String mc, int p, int s, Model model) {
        Pageable pageable = PageRequest.of(p, s);
        Page<Menu> menus;

        if(!mc.isEmpty())
        {
            //TODO 
            // plats = repoPlat.rechercher("%"+motCle+"%", pageable);
            menus = repoMenu.findAll(pageable);
        }
        else 
        {
            menus = repoMenu.findAll(pageable);
        }

        model.addAttribute("menus", menus.getContent());
        model.addAttribute("page",menus);
        model.addAttribute("motCle", mc);
        return "menus";
    }
}
