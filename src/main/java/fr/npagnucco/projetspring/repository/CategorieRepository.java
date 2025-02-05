package fr.npagnucco.projetspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.npagnucco.projetspring.model.Categorie;

/**
 * Interface qui permet d'avoir accès aux méthodes de type save, findbyId, findall pour les catégories
 * @author nathan
 */
public interface CategorieRepository extends JpaRepository<Categorie, Integer>{

}
