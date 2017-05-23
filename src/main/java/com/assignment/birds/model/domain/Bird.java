package com.assignment.birds.model.domain;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Set;

/**
 * Bird entity which will be stored in the repository.
 *
 * Created by Karan Malhotra on 26/4/17.
 */
public class Bird implements Serializable {

    @Id
    public String id;

    private String name;

    private String family;

    private Set<String> continents;

    private String added;

    private boolean visible;

    public Bird(BirdBuilder builder) {
        this.added = builder.added;
        this.continents = builder.continents;
        this.family = builder.family;
        this.name = builder.name;
        this.visible = builder.visible;
    }

    public Bird() {
    }

    public String getAdded() {
        return added;
    }

    public Set<String> getContinents() {
        return continents;
    }

    public String getFamily() {
        return family;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isVisible() {
        return visible;
    }

    @Override
    public String toString() {
        return "Bird{" +
                "added='" + added + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", family='" + family + '\'' +
                ", continents=" + continents +
                ", visible=" + visible +
                '}';
    }

    //Builder Class
    public static class BirdBuilder {
        private String name;
        private String family;
        private Set<String> continents;
        private String added;
        private boolean visible;

        public BirdBuilder(Set<String> continents, String family, String name) {
            this.continents = continents;
            this.family = family;
            this.name = name;
        }

        public BirdBuilder withAdded(String added) {
            this.added = added;
            return this;
        }

        public BirdBuilder withVisible(boolean visible) {
            this.visible = visible;
            return this;
        }

        public Bird build(){
            return new Bird(this);
        }
    }
}
