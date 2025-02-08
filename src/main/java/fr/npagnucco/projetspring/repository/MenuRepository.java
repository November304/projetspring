package fr.npagnucco.projetspring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.npagnucco.projetspring.model.Menu;

/**
 * Interface qui permet d'avoir accès aux méthodes de type save, findbyId, findall pour les menus
 * @author nathan
 */
public interface MenuRepository extends JpaRepository<Menu, Long>{
    @Query("SELECT m FROM fr.npagnucco.projetspring.model.Menu m WHERE (:minPrice is NULL OR m.prix >= :minPrice) AND (:maxPrice is NULL OR m.prix <= :maxPrice) AND (:nomMenu is NULL OR m.nom LIKE %:nomMenu%)")
    Page<Menu> findByFiltre(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice, @Param("nomMenu") String nomMenu, Pageable pageable);
}
