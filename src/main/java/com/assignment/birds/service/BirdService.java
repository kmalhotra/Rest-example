package com.assignment.birds.service;

import com.assignment.birds.model.domain.Bird;
import com.assignment.birds.model.dto.BirdDTO;
import com.assignment.birds.projection.BirdIdProjection;

import java.util.List;

/**
 * Created by Karan Malhotra on 26/4/17.
 */
public interface BirdService {

    /**
     * Service method to save a new bird to repository
     * @param birdDTO
     * @return Bird entity
     */
    Bird save(BirdDTO birdDTO);

    /**
     * Service method to fetch all Visible Birds from repository.
     * @return List of Ids
     */
    List<BirdIdProjection> fetchAllVisibleBirds();

    /**
     * Service method to find a bird based on the Id.
     * @param id,mandatory Parameter
     * @return Bird Entity
     */
    Bird findById(String id);

    /**
     * Service method to delete a bird basis the Id
     * @param id, mandatory Parameter.
     */
    void remove(String id);

}
