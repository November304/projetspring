package fr.npagnucco.projetspring.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

/**
 * Entité qui représente un menu en base de données.
 * @author nathan
 */
@Entity(name="menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private double prix;
    private String description;

    @ManyToMany
    @JoinTable(name = "menu_plat",
        joinColumns=@JoinColumn(name="menu_id",referencedColumnName="id"),
        inverseJoinColumns=@JoinColumn(name="plat_id",referencedColumnName="id")
    )
    private List<Plat> plats;

    public Menu() {
    }

    public Menu(String description, String nom, List<Plat> plats, double prix) {
        this.description = description;
        this.nom = nom;
        this.plats = plats;
        this.prix = prix;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Plat> getPlats() {
        return plats;
    }

    public void setPlats(List<Plat> plats) {
        this.plats = plats;
    }

    public int getNbCalories()
    {
        int nbCalories = 0;
        for(Plat plat : plats)
        {
            nbCalories += plat.getNb_calories();
        }
        return nbCalories;
    }



}
