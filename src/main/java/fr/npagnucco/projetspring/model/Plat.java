package fr.npagnucco.projetspring.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Entité qui représente un plat en base de données.
 * @author nathan
 */
@Entity(name="Plat")
public class Plat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 50, message="Le nom du plat doit contenir entre 2 et 50 caractères")
    private String nom;

    @NotNull(message="Le nombre de calories ne peut pas être vide")
    @Min(value=0, message="Le nombre de calories doit être positif ou nul")
    private int nb_calories;

    @NotNull(message="Le nombre de glucides ne peut pas être vide")
    @Min(value=0, message="Le nombre de glucides doit être positif ou nul")
    private int nb_glucides;

    @NotNull(message="Le nombre de lipides ne peut pas être vide")
    @Min(value=0, message="Le nombre de lipides doit être positif ou nul")
    private int nb_lipides;

    @NotNull(message="Le nombre de protéines ne peut pas être vide")
    @Min(value=0, message="Le nombre de protéines doit être positif ou nul")
    private int nb_proteines;

    @ManyToOne
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
