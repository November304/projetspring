package fr.npagnucco.projetspring.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.npagnucco.projetspring.model.Menu;
import fr.npagnucco.projetspring.repository.CategorieRepository;
import fr.npagnucco.projetspring.repository.MenuRepository;
import fr.npagnucco.projetspring.repository.PlatRepository;
import jakarta.validation.Valid;

/**
 * Controleur pour les CRUD des menus
 * @author nathan
 */
@Controller
public class MenuController {
    private final MenuRepository repoMenu;
    private final PlatRepository repoPlat;
    private final CategorieRepository repoCategorie;

    public MenuController(MenuRepository menuRepository, PlatRepository platRepository, CategorieRepository categorieRepository) {
        this.repoMenu = menuRepository;
        this.repoPlat = platRepository;
        this.repoCategorie = categorieRepository;
    }

    @GetMapping("/menus")
    public String listeMenus(
            @RequestParam(required = false) String priceRange,
            @RequestParam(required = false) String mc,
            @RequestParam(defaultValue = "0") int p,
            @RequestParam(defaultValue = "5") int s, 
            @RequestParam(name="act",defaultValue="") String action,
            @RequestParam(defaultValue="0") Long id,
            @RequestParam(defaultValue="") String error,
            Model model
            ) 
    {
        Pageable pageable = PageRequest.of(p, s);
        Page<Menu> menus;

        if(priceRange!=null || mc != null)
        {
            Double minPrice = null;
            Double maxPrice = null;
            if(priceRange!=null)
            {
                String[] parts = priceRange.split("-");
                if (parts.length == 2) {
                    try {
                        minPrice = Double.valueOf(parts[0]);
                        maxPrice = Double.valueOf(parts[1]);
                    } catch (NumberFormatException e) {
                        // Si exception on laisse les valeurs à null
                    }
                }
            }
            menus = repoMenu.findByFiltre(minPrice, maxPrice, mc, pageable);
        }
        else 
        {
            menus = repoMenu.findAll(pageable);
        }

        model.addAttribute("priceRange", priceRange);
        model.addAttribute("mc", mc);
        model.addAttribute("menus", menus.getContent());
        model.addAttribute("page",menus);
        if ( id>0 && (action.equals("new") || action.equals("mod")) ) {
            model.addAttribute("menu",
            this.repoMenu.getReferenceById(id) );
        } else if ( ! action.equals("del") ) {
            action = "";
        }
        if(!error.isEmpty())
        {
            model.addAttribute("erreur", error);
        }

        model.addAttribute("action", action);
        return "menus";
    }

    @GetMapping("/menuDelete")
    public String deleteMenu(Long id, int p, int s, String priceRange, String mc,
        RedirectAttributes attributes) 
    {
        this.repoMenu.deleteById(id);
        attributes.addAttribute("act", "del");
        attributes.addAttribute("priceRange", priceRange);
        attributes.addAttribute("mc", mc);
        attributes.addAttribute("p", p);
        attributes.addAttribute("s", s);
        return "redirect:/menus";
    }

    @GetMapping("/menuEdit")
    public String editerMenu(
        String mc,String priceRange,int p,int s,
        Long id,
        Model model
    ) 
    {
        if(id!=null)
        {
            Optional<Menu> optMenu = this.repoMenu.findById(id);
            if(optMenu.isPresent())
            {
                model.addAttribute("menu", optMenu.get());
            
            }
            else {
                return "redirect:/menus?error=NotFound";
            }
        }
        else 
        {
            model.addAttribute("menu", new Menu());
        }
        
        model.addAttribute("categories", this.repoCategorie.findAll());
        model.addAttribute("plats",this.repoPlat.findAll());
        model.addAttribute("priceRange",priceRange);
        model.addAttribute("mc", mc);
        model.addAttribute("p", p);
        model.addAttribute("s", s);

        return "menuEdit";
    }
    
    @PostMapping("/menuSave")
    public String sauverPlat(@Valid Menu menu,BindingResult bindingResult, int p, int s,String priceRange, String mc, Model model) {
        if(bindingResult.hasErrors())
        {
            model.addAttribute("menu", menu);
            model.addAttribute("plats",this.repoPlat.findAll());
            model.addAttribute("priceRange",priceRange);
            model.addAttribute("mc", mc);
            model.addAttribute("p", p);
            model.addAttribute("s", s);
            return "menuEdit";
        }
        this.repoMenu.save(menu);
        String action = (menu.getId()!=null?"mod":"new");
        return "redirect:/menus?p="+p+"&s="+s+"&priceRange="+priceRange+"&mc="+mc+"&act="+action+"&id="+menu.getId();
    }
}
