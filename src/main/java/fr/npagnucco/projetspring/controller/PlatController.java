package fr.npagnucco.projetspring.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.npagnucco.projetspring.model.Plat;
import fr.npagnucco.projetspring.repository.CategorieRepository;
import fr.npagnucco.projetspring.repository.PlatRepository;



/**
 * Controleur pour le CRUD des plats
 * @author nathan
 */
@Controller
public class PlatController {
    private final PlatRepository repoPlat;
    private final CategorieRepository repoCategorie;

    public PlatController(PlatRepository platRepository, CategorieRepository categorieRepository) {
        this.repoPlat = platRepository;
        this.repoCategorie = categorieRepository;
    }

    @GetMapping("/plats")
    public String listePlats(
        @RequestParam(defaultValue = "0") int p,
        @RequestParam(defaultValue = "10") int s, 
        Model model
    ) 
    {
        Pageable pageable = PageRequest.of(p, s);
        Page<Plat> plats;

        // if(!mc.isEmpty())
        // {
        //     //TODO 
        //     // plats = repoPlat.rechercher("%"+motCle+"%", pageable);
        //     plats = repoPlat.findAll(pageable);
        // }
        // else 
        // {
            plats = repoPlat.findAll(pageable);
        // }

        model.addAttribute("plats", plats.getContent());
        model.addAttribute("page",plats);
        return "plats";
    }

    @GetMapping("/platDelete")
    public String deletePlat(Long id, int p, int s , RedirectAttributes attributes) {
        this.repoPlat.deleteById(id);
        attributes.addAttribute("p", p);
        attributes.addAttribute("s", s);
        return "redirect:/plats";
    }

    @GetMapping("/platEdit")
    public String editerPlat(
        int p,int s,
        Long id,
        Model model
    ) 
    {
        if(id>0)
        {
            Optional<Plat> optPlat = this.repoPlat.findById(id);
            if(optPlat.isPresent())
            {
                model.addAttribute("plat", optPlat.get());
            }
            else {
                return "redirect:/plats";
            }
        }
        else 
        {
            model.addAttribute("plat", new Plat());
        }

        
        model.addAttribute("categories", this.repoCategorie.findAll());
        model.addAttribute("p", p);
        model.addAttribute("s", s);

        return "platEdit";
    }
    
    @PostMapping("/platSave")
    public String sauverPlat(Plat produit, int p, int s) {

        this.repoPlat.save(produit);
        return "redirect:/plats?p="+p+"&s="+s;
    }
}
