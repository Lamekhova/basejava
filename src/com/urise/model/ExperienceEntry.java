package com.urise.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * contains details on industry/educational experience
 * name - name of organization/educational center
 * startDate - start of learning/working
 */
public class ExperienceEntry implements Serializable {
    private final static long serialVersionUID = 1L;

    private Link name;
    private List<Position> positionsList;

    public ExperienceEntry(String name, String url, List<Position> positionsList) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(positionsList);
        this.name = new Link(name, url);
        this.positionsList = positionsList;
    }

    public Link getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExperienceEntry that = (ExperienceEntry) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(positionsList, that.positionsList);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, positionsList);
    }

    @Override
    public String toString() {
        return name + "\n" + positionsList.toString();
    }
}
