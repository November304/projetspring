package fr.npagnucco.projetspring.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;

/**
 * Entité qui représente un plat en base de données.
 * @author nathan
 */
@Entity(name="Plat")
public class Plat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private int nb_calories;
    private int nb_glucides;
    private int nb_lipides;
    private int nb_proteines;

    @OneToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @ManyToMany(mappedBy = "plats")
    private List<Menu> menus;

    public Plat() {
        this(null,null,0,0,0,0,"");
    }

    public Plat(Categorie categorie, List<Menu> menus, int nb_calories, int nb_glucides, int nb_lipides, int nb_proteines, String nom) {
        this.categorie = categorie;
        this.menus = menus;
        this.nb_calories = nb_calories;
        this.nb_glucides = nb_glucides;
        this.nb_lipides = nb_lipides;
        this.nb_proteines = nb_proteines;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNb_calories() {
        return nb_calories;
    }

    public void setNb_calories(int nb_calories) {
        this.nb_calories = nb_calories;
    }

    public int getNb_glucides() {
        return nb_glucides;
    }

    public void setNb_glucides(int nb_glucides) {
        this.nb_glucides = nb_glucides;
    }

    public int getNb_lipides() {
        return nb_lipides;
    }

    public void setNb_lipides(int nb_lipides) {
        this.nb_lipides = nb_lipides;
    }

    public int getNb_proteines() {
        return nb_proteines;
    }

    public void setNb_proteines(int nb_proteines) {
        this.nb_proteines = nb_proteines;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }
}
