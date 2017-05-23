package com.assignment.birds.model.dto;

import com.assignment.birds.model.request.BirdApiRequest;

import java.util.Set;

/**
 * DTO class to move data between layers.
 *
 * Created by Karan Malhotra on 26/4/17.
 */
public class BirdDTO {

    private String name;
    private String family;
    private Set<String> continents;
    private String added;
    private boolean visible;

    public BirdDTO(String added, Set<String> continents, String family, String name, boolean visible) {
        this.added = added;
        this.continents = continents;
        this.family = family;
        this.name = name;
        this.visible = visible;
    }

    public String getAdded() {
        return added;
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public Set<String> getContinents() {
        return continents;
    }

    public void setContinents(Set<String> continents) {
        this.continents = continents;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public static BirdDTO buildFromRequest(BirdApiRequest apiRequest) {
        return new BirdDTO(apiRequest.getAdded(), apiRequest.getContinents(), apiRequest.getFamily(), apiRequest.getName(), apiRequest.isVisible());
    }

    @Override
    public String toString() {
        return "BirdDTO{" +
                "added='" + added + '\'' +
                ", name='" + name + '\'' +
                ", family='" + family + '\'' +
                ", continents=" + continents +
                ", visible=" + visible +
                '}';
    }
}
