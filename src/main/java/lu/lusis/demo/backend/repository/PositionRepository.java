package lu.lusis.demo.backend.repository;

import lu.lusis.demo.backend.data.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PositionRepository extends JpaRepository<Position,Integer> {

    /*
    Renvoi les distributeurs compris dans un cercle de rayon donné
    @Param:
    @mapLongitude: Double,
    @mapLatitude: Double,
    @distance: Double

ST_WITHIN(POINT, FIGURE) Renvoie vrai ou faux selon que le point se trouve dans la figure FIGURE
ST_BUFFER(POINT, RAYON)  Créer un cercle de centre POINT et de rayon RAYON

@Query permet l'exécution de requêtes en SQL natif, personnalisées.
    L'attribut nativeQuery est necessaire à la bonne exécution de cette fonctionnalité.
     */
    @Query(value =
            "SELECT *  FROM position p  WHERE ST_WITHIN(point(p.longitude,p.latitude), ST_Buffer(point(?1, ?2), ?3)) ", nativeQuery = true)
    List<Position> findByDistance(Double longitude, Double latitude, Double distance);
}
