package com.assignment.birds.service;

import com.assignment.birds.exception.BirdNotFoundException;
import com.assignment.birds.model.domain.Bird;
import com.assignment.birds.model.dto.BirdDTO;
import com.assignment.birds.projection.BirdIdProjection;
import com.assignment.birds.repository.BirdRepository;
import com.assignment.birds.service.impl.BirdServiceImpl;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;


/**
 * Created by Karan Malhotra on 26/4/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class BirdServiceTest {

    @Mock
    private static BirdRepository birdsRepository ;

    @InjectMocks
    private BirdServiceImpl birdService;

    String missingBirdId = "1234";

    String visibleBirdId = "234";
    String invisibleBirdId = "345";

    String visibleBirdName = "Karan";
    String invisibleBirdName = "Malhotra";

    String visibleBirdFamily = "Latin";
    String invisibleBirdFamily = "Spanish";

    Set<String> visibleBirdContinents = new HashSet<String>() {{ add("Europe"); }};
    Set<String> invisisbleBirdContinents = new HashSet<String>() {{ add("Asia"); }};

    String visibleBirdAdded = DateTime.now(DateTimeZone.UTC).toString(DateTimeFormat.forPattern("yyyy-MM-dd"));
    String invisibleBirdAdded = DateTime.now(DateTimeZone.UTC).toString(DateTimeFormat.forPattern("yyyy-MM-dd"));

    Bird visibleBird = new Bird.BirdBuilder(visibleBirdContinents, visibleBirdFamily, visibleBirdName)
            .withAdded(visibleBirdAdded)
            .withVisible(Boolean.TRUE)
            .build();

    Bird invisibleBird = new Bird.BirdBuilder(invisisbleBirdContinents, invisibleBirdFamily, invisibleBirdName)
            .withAdded(invisibleBirdAdded)
            .withVisible(Boolean.FALSE)
            .build();

    BirdDTO birdDTO = new BirdDTO(visibleBirdAdded, visibleBirdContinents, visibleBirdFamily, visibleBirdName, Boolean.TRUE);

    @Before
    public void beforeTests() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = BirdNotFoundException.class)
    public void testFindByIdThrowsExceptionWhenBirdIsNotFound() {
        when(birdsRepository.findOne(missingBirdId)).thenReturn(null);
        birdService.findById(missingBirdId);
    }

    @Test
    public void testFindByIdForVisibleBird() {
        when(birdsRepository.findOne(visibleBirdId)).thenReturn(visibleBird);
        Bird bird = birdService.findById(visibleBirdId);

        assertEquals(bird.getName(), visibleBirdName);
        assertEquals(bird.getFamily(), visibleBirdFamily);
        assertEquals(bird.getAdded(), visibleBirdAdded);
        assertEquals(bird.getContinents(), visibleBirdContinents);
        assertEquals(bird.isVisible(), Boolean.TRUE);

    }

    @Test
    public void testFindByIdForInvisibleBird() {
        when(birdsRepository.findOne(invisibleBirdId)).thenReturn(invisibleBird);
        Bird bird = birdService.findById(invisibleBirdId);

        assertEquals(bird.getName(), invisibleBirdName);
        assertEquals(bird.getFamily(), invisibleBirdFamily);
        assertEquals(bird.getAdded(), invisibleBirdAdded);
        assertEquals(bird.getContinents(), invisisbleBirdContinents);
        assertEquals(bird.isVisible(), Boolean.FALSE);
    }

    @Test
    public void testFindAllVisibleBirds() {

        List<BirdIdProjection> idProjectionList = new ArrayList<BirdIdProjection>() {{ add(new BirdIdProjection() {
            @Override
            public String getId() {
                return visibleBirdId;
            }
        }); }};

        when(birdsRepository.findAllByVisible(Boolean.TRUE)).thenReturn(idProjectionList);

        List<BirdIdProjection> returnList = birdService.fetchAllVisibleBirds();

        assertEquals(returnList.size(),1);
        assertEquals(returnList.get(0).getId(), visibleBirdId);

        when(birdsRepository.findOne(returnList.get(0).getId())).thenReturn(visibleBird) ;

        assertEquals(visibleBird.getName(), visibleBirdName);
        assertEquals(visibleBird.getFamily(), visibleBirdFamily);
        assertEquals(visibleBird.getAdded(), visibleBirdAdded);
        assertEquals(visibleBird.isVisible(), Boolean.TRUE);
        assertEquals(visibleBird.getContinents(), visibleBirdContinents);
    }

    @Test
    public void testSave() {
        when(birdsRepository.save(any(Bird.class))).thenReturn(visibleBird);

        Bird bird = birdService.save(birdDTO);

        assertEquals(bird.getName(), visibleBirdName);
        assertEquals(bird.getFamily(), visibleBirdFamily);
        assertEquals(bird.getAdded(), visibleBirdAdded);
        assertEquals(bird.isVisible(), Boolean.TRUE);
        assertEquals(bird.getContinents(), visibleBirdContinents);
    }

    @Test(expected = BirdNotFoundException.class)
    public void testRemoveThrowsBirdNotFoundExceptionWhenMissingBirdIsPassedForDeletion() {
        when(birdsRepository.findOne(missingBirdId)).thenReturn(null);
        birdService.remove(missingBirdId);
    }
}
