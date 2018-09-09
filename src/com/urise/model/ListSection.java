package com.urise.model;

import java.util.*;

public class ListSection extends Section implements Iterable<String> {
    private final static long serialVersionUID = 1L;

    private List<String> sections;

    public ListSection() {
        this.sections = new ArrayList<>();
    }

    public ListSection(List<String> sections) {
        this.sections = sections;
    }

    public List<String> getSections() {
        return sections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(sections, that.sections);
    }

    @Override
    public int hashCode() {

        return Objects.hash(sections);
    }

    @Override
    public String toString() {
        return sections.toString();
    }

    @Override
    public Iterator<String> iterator() {
        return this.sections.iterator();
    }
}
