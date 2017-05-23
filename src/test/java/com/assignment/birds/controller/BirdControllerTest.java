package com.assignment.birds.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.assignment.birds.exception.BirdNotFoundException;
import com.assignment.birds.model.domain.Bird;
import com.assignment.birds.model.request.BirdApiRequest;
import com.assignment.birds.projection.BirdIdProjection;
import com.assignment.birds.service.BirdService;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Karan Malhotra on 26/4/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class BirdControllerTest {

    @Mock
    private static BirdService birdService;

    @InjectMocks
    private BirdController controller;

    MockMvc mockMvc;

    String birdId = "123";
    String anotherBirdId = "234";

    BirdApiRequest apiRequest = new BirdApiRequest();
    ObjectMapper mapper = new ObjectMapper();

    Bird bird = new Bird.BirdBuilder(new HashSet<String>() {{ add("Europe"); }}, "Latin", "Karan")
            .withAdded(DateTime.now(DateTimeZone.UTC).toString(DateTimeFormat.forPattern("yyyy-MM-dd")))
            .withVisible(Boolean.TRUE)
            .build();

    @Before
    public void beforeTests() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();

        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    }

    @Test
    public void testGetAllBirds() throws Exception {
        List<BirdIdProjection> idProjectionList = new ArrayList<BirdIdProjection>() {{ add(new BirdIdProjection() {
            @Override
            public String getId() {
                return birdId;
            }
        });
            add(new BirdIdProjection() {
                @Override
                public String getId() {
                    return anotherBirdId;
                }
            });}};

        when(birdService.fetchAllVisibleBirds()).thenReturn(idProjectionList);
        this.mockMvc.perform(get("/v1/birds"))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":\""+birdId + "\"},{\"id\":\""+anotherBirdId + "\"}]"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    //@Test
    public void testGetAllBirdsRespondsWith500WhenAnyExceptionOccurs() throws Exception{
        when(birdService.fetchAllVisibleBirds()).thenThrow(Exception.class);
        this.mockMvc.perform(get("/v1/birds"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testGetBirdByIdReturn404WhenBirdIsNotFound() throws Exception{
        when(birdService.findById(birdId)).thenThrow(BirdNotFoundException.class);
        this.mockMvc.perform(get("/v1/birds/"+birdId))
                .andExpect(status().isNotFound());

    }

    @Test
    public void testGetBirdByIdReturn200AndBirdInContentIfBirdIsFound() throws Exception {
        when(birdService.findById(birdId)).thenReturn(bird);
        this.mockMvc.perform(get("/v1/birds/"+birdId))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(bird)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetBirdByIdReturn500WhenAnyExceptionOccurs() throws Exception {
        when(birdService.findById(birdId)).thenThrow(Exception.class);
        this.mockMvc.perform(get("/v1/birds/"+birdId))
                .andExpect(status().isInternalServerError());
    }


    @Test
    public void testPostBirdReturn201IfBirdIsSuccessfullyAddedToRepository() throws Exception {

    }

    @Test
    public void testPostBirdReturn400IfObjectIsEmpty() throws Exception {
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        apiRequest.setName("Karan");
        String requestJson = ow.writeValueAsString(apiRequest );

        this.mockMvc.perform(post("/v1/birds").content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPostBirdReturn400IfOnlyNameIsSent() throws Exception {
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        apiRequest.setName("Karan");
        String requestJson = ow.writeValueAsString(apiRequest );

        this.mockMvc.perform(post("/v1/birds").content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPostBirdReturn400IfOnlyNameAndFamilyIsSent() throws Exception {
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        apiRequest.setName("Karan");
        apiRequest.setFamily("Latin");
        String requestJson = ow.writeValueAsString(apiRequest );

        this.mockMvc.perform(post("/v1/birds").content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPostBirdReturn400IfOnlyNameAndFamilyAndContinentIsSentButContinentElementIsNull() throws Exception {
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        apiRequest.setName("Karan");
        apiRequest.setFamily("Latin");
        apiRequest.setContinents(new HashSet<String>() {{ add(null) ;}});

        String requestJson = ow.writeValueAsString(apiRequest );

        this.mockMvc.perform(post("/v1/birds").content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPostBirdReturn400IfOnlyNameAndFamilyAndContinentIsSentButContinentElementIsBlank() throws Exception {
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        apiRequest.setName("Karan");
        apiRequest.setFamily("Latin");
        apiRequest.setContinents(new HashSet<String>() {{ add("") ;}});

        String requestJson = ow.writeValueAsString(apiRequest );

        this.mockMvc.perform(post("/v1/birds").content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPostBirdReturn400IfOnlyNameAndFamilyAndContinentIsSentButContinentElementIsEmpltySpace() throws Exception {
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        apiRequest.setName("Karan");
        apiRequest.setFamily("Latin");
        apiRequest.setContinents(new HashSet<String>() {{ add(" ") ;}});

        String requestJson = ow.writeValueAsString(apiRequest );

        this.mockMvc.perform(post("/v1/birds").content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPostBirdReturn400IfOnlyNameAndFamilyAndContinentIsSentButFamilyIsBlank() throws Exception {
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        apiRequest.setName("Karan");
        apiRequest.setFamily("");
        apiRequest.setContinents(new HashSet<String>() {{ add("Europe") ;}});

        String requestJson = ow.writeValueAsString(apiRequest );

        this.mockMvc.perform(post("/v1/birds").content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPostBirdReturn400IfOnlyNameAndFamilyAndContinentIsSentButFamilyIsEmptySpace() throws Exception {
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        apiRequest.setName("Karan");
        apiRequest.setFamily(" ");
        apiRequest.setContinents(new HashSet<String>() {{ add("Europe") ;}});

        String requestJson = ow.writeValueAsString(apiRequest );

        this.mockMvc.perform(post("/v1/birds").content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPostBirdReturn400IfOnlyNameAndFamilyAndContinentIsSentButNameIsBlank() throws Exception {
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        apiRequest.setName("");
        apiRequest.setFamily("Latin");
        apiRequest.setContinents(new HashSet<String>() {{ add("Europe") ;}});

        String requestJson = ow.writeValueAsString(apiRequest );

        this.mockMvc.perform(post("/v1/birds").content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPostBirdReturn400IfOnlyNameAndFamilyAndContinentIsSentButNameIsEmptySpace() throws Exception {
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        apiRequest.setName(" ");
        apiRequest.setFamily("Latin");
        apiRequest.setContinents(new HashSet<String>() {{ add("Europe") ;}});

        String requestJson = ow.writeValueAsString(apiRequest );

        this.mockMvc.perform(post("/v1/birds").content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPostBirdReturn500IfAnyExceptionOccurs() throws Exception {
        when(birdService.save(anyObject())).thenThrow(Exception.class);

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        apiRequest.setName("Karan");
        apiRequest.setFamily("Latin");
        apiRequest.setContinents(new HashSet<String>() {{ add("Europe") ;}});

        String requestJson = ow.writeValueAsString(apiRequest );

        this.mockMvc.perform(post("/v1/birds").content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

    }

    @Test
    public void testDeleteBirdReturn200IfBirdIsDeletedSuccessfully() throws Exception {
        this.mockMvc.perform(delete("/v1/birds/"+birdId))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteBirdReturn404IfBirdDoesNotExists() throws Exception {
        doThrow(BirdNotFoundException.class).doNothing().when(birdService).remove(birdId);
        this.mockMvc.perform(delete("/v1/birds/"+birdId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteBirdReturn500IfAnyExceptionOccurs() throws Exception {
        doThrow(Exception.class).doNothing().when(birdService).remove(birdId);
        this.mockMvc.perform(delete("/v1/birds/"+birdId))
                .andExpect(status().isInternalServerError());

    }
}
