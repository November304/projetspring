package fr.npagnucco.projetspring.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.npagnucco.projetspring.model.Plat;
import fr.npagnucco.projetspring.repository.PlatRepository;



/**
 * Controleur pour le CRUD des plats
 * @author nathan
 */
@Controller
public class PlatController {
    private final PlatRepository repoPlat;

    public PlatController(PlatRepository platRepository) {
        this.repoPlat = platRepository;
    }

    @GetMapping("/plats")
    public String listePlats(String mc, int p, int s, Model model) {
        Pageable pageable = PageRequest.of(p, s);
        Page<Plat> plats;

        if(!mc.isEmpty())
        {
            //TODO 
            // plats = repoPlat.rechercher("%"+motCle+"%", pageable);
            plats = repoPlat.findAll(pageable);
        }
        else 
        {
            plats = repoPlat.findAll(pageable);
        }

        model.addAttribute("plats", plats.getContent());
        model.addAttribute("page",plats);
        model.addAttribute("motCle", mc);
        return "plats";
    }

    @GetMapping("/platDelete")
    public String getMethodName(int id, int p, int s , String mc, RedirectAttributes attributes) {
        this.repoPlat.deleteById(id);
        attributes.addAttribute("p", p);
        attributes.addAttribute("s", s);
        attributes.addAttribute("mc", mc);
        return "redirect:/plats";
    }

    @GetMapping("/platEdit")
    public String editerProduit(
        String mc,int p,int s,
        int id,
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

        model.addAttribute("mc", mc);
        model.addAttribute("p", p);
        model.addAttribute("s", s);

        return "platEdit";
    }
    
    @PostMapping("/platSave")
    public String sauverProduit(Plat produit, int p, int s, String mc) {

        this.repoPlat.save(produit);
        return "redirect:/plats?p="+p+"&s="+s+"&mc="+mc;
    }
}
