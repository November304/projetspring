package fr.npagnucco.projetspring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.npagnucco.projetspring.model.Plat;

/**
 * Interface qui permet d'avoir accès aux méthodes de type save, findbyId, findall pour les plats
 * @author nathan
 */
public interface PlatRepository extends JpaRepository<Plat, Integer> {
    @Query("SELECT p FROM Plat p WHERE (:cid is NULL OR p.categorie.id = :cid) AND (:mincal IS NULL OR p.nb_calories>:mincal) AND (:maxcal IS NULL OR p.nb_calories<:maxcal)")
    Page<Plat> findByFiltre(@Param("cid") Integer cid,@Param("mincal") Integer mincal,@Param("maxcal") Integer maxcal, Pageable pageable);


}
