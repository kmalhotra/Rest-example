package com.assignment.birds.repository;

import com.assignment.birds.model.domain.Bird;
import com.assignment.birds.projection.BirdIdProjection;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * This is the bird repository class used to perform various CRUD and custom implementations controlling
 * the interaction with the Repository.
 *
 * Created by Karan Malhotra on 26/4/17.
 */
public interface BirdRepository extends MongoRepository<Bird,String> {

    // Method to fetch on Visible Birds from Repository.
    List<BirdIdProjection> findAllByVisible(boolean visible);
}
