package com.urise.model;

import java.util.*;

public class ListSection extends Section {
    private final static long serialVersionUID = 1L;

    private List<String> sections;

    public ListSection() {
    }

    public ListSection(List<String> sections) {
        this.sections = sections;
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
}
