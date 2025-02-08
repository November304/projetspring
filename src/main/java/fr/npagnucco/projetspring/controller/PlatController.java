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

import fr.npagnucco.projetspring.model.Plat;
import fr.npagnucco.projetspring.repository.CategorieRepository;
import fr.npagnucco.projetspring.repository.PlatRepository;
import jakarta.validation.Valid;



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
        @RequestParam(defaultValue = "") Long cid,
        @RequestParam(defaultValue = "") Long mincal,
        @RequestParam(defaultValue = "") Long maxcal,
        @RequestParam(name="act",defaultValue="") String action,
        @RequestParam(defaultValue="0") Long id,
        Model model
    ) 
    {
        Pageable pageable = PageRequest.of(p, s);
        Page<Plat> plats;

        if(cid!=null || mincal!=null || maxcal!=null)
        {
            plats = repoPlat.findByFiltre(cid, mincal, maxcal, pageable);
        }
        else 
        {
            plats = repoPlat.findAll(pageable);
        }
        
        model.addAttribute("categories", this.repoCategorie.findAll());
        model.addAttribute("plats", plats.getContent());
        model.addAttribute("page",plats);
        model.addAttribute("cid", cid);
        model.addAttribute("mincal", mincal);
        model.addAttribute("maxcal", maxcal);
        if ( id>0 && (action.equals("new") || action.equals("mod")) ) {
            model.addAttribute("plat",
            this.repoPlat.getReferenceById(id) );
        } else if ( ! action.equals("del") ) {
            action = "";
        }
        model.addAttribute("action", action);
        return "plats";
    }

    @GetMapping("/platDelete")
    public String deletePlat(Long id, 
        int p, int s, Long cid, Long mincal, Long maxcal,
        RedirectAttributes attributes
    ) {
        this.repoPlat.deleteById(id);
        attributes.addAttribute("act","del");
        attributes.addAttribute("p", p);
        attributes.addAttribute("s", s);
        attributes.addAttribute("cid", cid);
        attributes.addAttribute("mincal", mincal);
        attributes.addAttribute("maxcal", maxcal);
        return "redirect:/plats";
    }

    @GetMapping("/platEdit")
    public String editerPlat(
        int p,int s,Long cid, Long mincal, Long maxcal,
        Long id,
        Model model
    ) 
    {
        if(id!=null)
        {
            Optional<Plat> optPlat = this.repoPlat.findById(id);
            if(optPlat.isPresent())
            {
                model.addAttribute("plat", optPlat.get());
            }
            else {
                return "redirect:/plats?error=NotFound";
            }
        }
        else 
        {
            model.addAttribute("plat", new Plat());
        }

        
        model.addAttribute("categories", this.repoCategorie.findAll());
        model.addAttribute("p", p);
        model.addAttribute("s", s);
        model.addAttribute("cid", cid);
        model.addAttribute("mincal", mincal);
        model.addAttribute("maxcal", maxcal);
        return "platEdit";
    }
    
    @PostMapping("/platSave")
    public String sauverPlat(int p, int s,Long cid, Long mincal, Long maxcal,
        @Valid Plat plat,BindingResult bindingResult,Model model, 
        RedirectAttributes attributes)
    {
        if(bindingResult.hasErrors())
        {
            model.addAttribute("plat", plat);
            model.addAttribute("categories", this.repoCategorie.findAll());
            model.addAttribute("p", p);
            model.addAttribute("s", s);
            model.addAttribute("cid", cid);
            model.addAttribute("mincal", mincal);
            model.addAttribute("maxcal", maxcal);
            return "platEdit";
        }
        String action = (plat.getId()!=null?"mod":"new");
        this.repoPlat.save(plat);
        attributes.addAttribute("act",action);
        attributes.addAttribute("id", plat.getId());
        attributes.addAttribute("p", p);
        attributes.addAttribute("s", s);
        attributes.addAttribute("cid", cid);
        attributes.addAttribute("mincal", mincal);
        attributes.addAttribute("maxcal", maxcal);
        return "redirect:/plats";
    }
}
