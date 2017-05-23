package com.assignment.birds.controller;

import com.assignment.birds.model.domain.Bird;
import com.assignment.birds.model.dto.BirdDTO;
import com.assignment.birds.model.request.BirdApiRequest;
import com.assignment.birds.projection.BirdIdProjection;
import com.assignment.birds.service.BirdService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller to handle all birds crud operation request.
 * Created by Karan Malhotra on 26/4/17.
 */
@RestController
@RequestMapping(value = "/v1/birds")
public class BirdController extends BaseController{

    private static final Logger logger = LogManager.getLogger(BirdController.class);

    @Autowired
    private BirdService birdService;

    /**
     * The endpoint is used to fetch all visible birds from birds repository.
     * @return List of bird ids
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BirdIdProjection>> getAllBirds(){
        logger.trace("Find all Birds invoked.");
        List<BirdIdProjection> birds = birdService.fetchAllVisibleBirds();
        logger.trace("find all birds completed succesfully");
        return ResponseEntity.status(HttpStatus.OK).body(birds);
    }

    /**
     * This endpoint is used for Birds for a particular Id
     *
     * @param id, mandatory field bird Id
     * @return Bird Entity
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Bird> getBird(@PathVariable(value = "id",required = true) String id ){
        logger.trace("Get Bird by Id invoked, with id {}" , id);
        Bird bird = birdService.findById(id);
        logger.trace("Get Bird by Id finished, with id {} " , id);
        return ResponseEntity.status(HttpStatus.OK).body(bird);
    }

    /**
     * This endpoint is used to save bird entity in birds repository
     *
     * @param birdsApiRequest
     * @return saved Bird Entity
     */
    @RequestMapping(method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addBird(@Valid @RequestBody BirdApiRequest birdsApiRequest ) {
        logger.trace("Add Bird invoked ");
        BirdDTO birdDTO = BirdDTO.buildFromRequest(birdsApiRequest);
        Bird bird = birdService.save(birdDTO);
        logger.trace("Add Bird finished");
        return ResponseEntity.status(HttpStatus.CREATED).body(bird);
    }

    /**
     * This endpoint is used to remove a bird entity from repository.
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteBird(@PathVariable(value = "id",required = true) String id ){
        logger.trace("Delete Bird invoked with Id {}", id);
        birdService.remove(id);
        logger.trace("Delete Bird finished, with id {}" , id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
