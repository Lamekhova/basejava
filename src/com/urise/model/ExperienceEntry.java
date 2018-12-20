package com.urise.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.*;

/**
 * contains details on industry/educational experience
 * name - name of organization/educational center
 * startDate - start of learning/working
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ExperienceEntry implements Serializable, Iterable {
    private final static long serialVersionUID = 1L;

    public static final ExperienceEntry EMPTY = new ExperienceEntry("", "", Position.EMPTY);

    private Link name;
    private List<Position> positionsList = new ArrayList<>();

    public ExperienceEntry() {
    }

    public ExperienceEntry(String name, String url, Position... positions) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(positions);
        this.name = new Link(name, url);
        this.positionsList = Arrays.asList(positions);
    }

    public ExperienceEntry(String name, String url, List<Position> positions) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(positions);
        this.name = new Link(name, url);
        this.positionsList = new ArrayList<>(positions);
    }

    public ExperienceEntry(Link name, List<Position> positionsList) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(positionsList);
        this.name = name;
        this.positionsList = new ArrayList<>(positionsList);
    }

    public Link getName() {
        return name;
    }

    public void setName(Link name) {
        this.name = name;
    }

    public List<Position> getPositionsList() {
        return positionsList;
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

    @Override
    public Iterator iterator() {
        return positionsList.iterator();
    }
}
