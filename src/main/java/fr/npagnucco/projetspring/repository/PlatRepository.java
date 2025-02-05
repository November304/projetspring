package fr.npagnucco.projetspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.npagnucco.projetspring.model.Plat;

/**
 * Interface qui permet d'avoir accès aux méthodes de type save, findbyId, findall pour les plats
 * @author nathan
 */
public interface PlatRepository extends JpaRepository<Plat, Integer> {

}
