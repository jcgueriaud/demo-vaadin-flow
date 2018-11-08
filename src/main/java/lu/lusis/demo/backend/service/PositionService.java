package lu.lusis.demo.backend.service;

import lu.lusis.demo.backend.data.Position;
import lu.lusis.demo.backend.repository.PositionRepository;
import lu.lusis.demo.utils.DistanceCalculator;
import lu.lusis.demo.utils.HasLogger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service position
 */
@Service
public class PositionService implements HasLogger {

    private final PositionRepository positionRepository;


    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    /**
     * Recherche les positions dans un rayon de x kms
     * Ne pas utiliser en production si recherche JAVA
     *
     * @param longitude
     * @param latitude
     * @param distance
     * @return
     */
    public List<Position> findByDistance(Double longitude, Double latitude, Double distance){
        // if Mysql db
        //return positionRepository.findByDistance(longitude, latitude, distance);
        // else for demo
        List<Position> positions = positionRepository.findAll();
        return positions.stream().filter(position -> DistanceCalculator.distance(latitude,longitude,position ) < distance).collect(Collectors.toList());
    }
}
