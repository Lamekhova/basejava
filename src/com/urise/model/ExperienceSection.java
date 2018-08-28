package com.urise.model;

import java.util.*;

public class ExperienceSection implements Section {

    private List<ExperienceEntry> experienceEntries;

    public ExperienceSection(List<ExperienceEntry> experienceEntries) {
        Objects.requireNonNull(experienceEntries);
        this.experienceEntries = experienceEntries;

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
}
