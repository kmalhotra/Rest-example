package com.assignment.birds.model.request;

import com.assignment.birds.validator.NotEmptyFields;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * This class is the apiRequest used to add a bird to the Birds repository and validate for sanitization of input data.
 *
 * Created by Karan Malhotra on 26/4/17.
 */
public class BirdApiRequest {

    public BirdApiRequest() {
    }

    // Represents the name of the bird.
    @NotNull
    @NotBlank
    private String name;

    // Represents the family to which this bird belongs to.
    @NotNull
    @NotBlank
    private String family;

    // Represents the continents where this bird can be found.
    @NotNull
    @Size(min = 1)
    // Custom validation to handle cases where collection may contain blank entries [" "] OR [""]
    @NotEmptyFields()
    private Set<String> continents;

    // Represents the added date in UTC defaulting to current date.
    private String added = DateTime.now(DateTimeZone.UTC).toString(DateTimeFormat.forPattern("yyyy-MM-dd"));

    // Represents the visibility of the bird in UTC defaulting to False.
    private boolean visible = Boolean.FALSE;

    public String getAdded() {
        return added;
    }

    public Set<String> getContinents() {
        return continents;
    }

    public String getFamily() {
        return family;
    }

    public String getName() {
        return name;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public void setContinents(Set<String> continents) {
        this.continents = continents;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
