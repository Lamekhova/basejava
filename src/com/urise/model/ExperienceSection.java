package com.urise.model;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ExperienceSection extends Section implements Iterable {
    private final static long serialVersionUID = 1L;

    private List<ExperienceEntry> experienceEntries;

    public ExperienceSection() {
    }

    public ExperienceSection(List<ExperienceEntry> experienceEntries) {
        Objects.requireNonNull(experienceEntries);
        this.experienceEntries = experienceEntries;

    }

    public List<ExperienceEntry> getExperienceEntries() {
        return experienceEntries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExperienceSection that = (ExperienceSection) o;
        return Objects.equals(experienceEntries, that.experienceEntries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(experienceEntries);
    }

    @Override
    public String toString() {
        return experienceEntries.toString();
    }

    @Override
    public Iterator iterator() {
        return this.experienceEntries.iterator();
    }
}
