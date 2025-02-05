package fr.npagnucco.projetspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.npagnucco.projetspring.model.Menu;

/**
 * Interface qui permet d'avoir accès aux méthodes de type save, findbyId, findall pour les menus
 * @author nathan
 */
public interface MenuRepository extends JpaRepository<Menu, Integer>{

}
