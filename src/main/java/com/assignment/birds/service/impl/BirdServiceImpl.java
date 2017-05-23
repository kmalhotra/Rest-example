package com.assignment.birds.service.impl;

import com.assignment.birds.exception.BirdNotFoundException;
import com.assignment.birds.model.domain.Bird;
import com.assignment.birds.model.dto.BirdDTO;
import com.assignment.birds.projection.BirdIdProjection;
import com.assignment.birds.repository.BirdRepository;
import com.assignment.birds.service.BirdService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.text.MessageFormat;
import java.util.List;

/**
 * Created by Karan Malhotra on 26/4/17.
 */
@Service
public class BirdServiceImpl implements BirdService {

    private static final Logger logger = LogManager.getLogger(BirdServiceImpl.class);

    @Autowired
    private BirdRepository birdsRepository;

    public Bird save(@NotNull BirdDTO birdDTO) {
        Bird bird = new Bird.BirdBuilder(birdDTO.getContinents(), birdDTO.getFamily(), birdDTO.getName())
                    .withAdded(birdDTO.getAdded())
                    .withVisible(birdDTO.isVisible())
                    .build();
        logger.debug("Going to save bird in repository, details :- {}", birdDTO);
        return birdsRepository.save(bird);
    }

    public List<BirdIdProjection> fetchAllVisibleBirds() {
        return birdsRepository.findAllByVisible(Boolean.TRUE);
    }

    public Bird findById(@NotNull String id) {
        logger.debug("Going to find bird with Id {}", id);
        Bird bird = birdsRepository.findOne(id);
        if (null == bird) {
            logger.info("Bird not found with id {}", id);
            throw new BirdNotFoundException(MessageFormat.format("Bird not found with Id {0}", id));
        }
        return bird;
    }

    public void remove(@NotNull String id) {
        Bird bird = findById(id);
        logger.debug("Going to remove bird from repository with Id {}", id);
        birdsRepository.delete(bird);
        logger.debug("Bird removed from repository with Id {}", id);
    }
}
