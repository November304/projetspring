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

    public MenuController(MenuRepository menuRepository, PlatRepository platRepository) {
        this.repoMenu = menuRepository;
        this.repoPlat = platRepository;
    }

    @GetMapping("/menus")
    public String listeMenus(
            @RequestParam(defaultValue = "") String mc,
            @RequestParam(defaultValue = "0") int p,
            @RequestParam(defaultValue = "5") int s, 
            @RequestParam(name="act",defaultValue="") String action,
            @RequestParam(defaultValue="0") Long id,
            Model model
            ) 
    {
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
        if ( id>0 && (action.equals("new") || action.equals("mod")) ) {
            model.addAttribute("menu",
            this.repoMenu.getReferenceById(id) );
        } else if ( ! action.equals("del") ) {
            action = "";
        }
        model.addAttribute("action", action);
        return "menus";
    }

    @GetMapping("/menuDelete")
    public String deleteMenu(Long id, int p, int s , String mc, RedirectAttributes attributes) {
        this.repoMenu.deleteById(id);
        attributes.addAttribute("act", "del");
        attributes.addAttribute("p", p);
        attributes.addAttribute("s", s);
        attributes.addAttribute("mc", mc);
        return "redirect:/menus";
    }

    @GetMapping("/menuEdit")
    public String editerMenu(
        String mc,int p,int s,
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
                return "redirect:/menus";
            }
        }
        else 
        {
            model.addAttribute("menu", new Menu());
        }

        model.addAttribute("plats",this.repoPlat.findAll());
        model.addAttribute("mc", mc);
        model.addAttribute("p", p);
        model.addAttribute("s", s);

        return "menuEdit";
    }
    
    @PostMapping("/menuSave")
    public String sauverPlat(@Valid Menu menu,BindingResult bindingResult, int p, int s, String mc, Model model) {
        if(bindingResult.hasErrors())
        {
            model.addAttribute("menu", menu);
            model.addAttribute("plats",this.repoPlat.findAll());
            model.addAttribute("mc", mc);
            model.addAttribute("p", p);
            model.addAttribute("s", s);
            return "menuEdit";
        }
        this.repoMenu.save(menu);
        String action = (menu.getId()!=null?"mod":"new");
        return "redirect:/menus?p="+p+"&s="+s+"&mc="+mc+"&act="+action+"&id="+menu.getId();
    }
}
