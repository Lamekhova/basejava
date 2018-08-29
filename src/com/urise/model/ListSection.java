package com.urise.model;

import java.util.*;

public class ListSection extends Section {
    private final static long serialVersionUID = 1L;

    private List<String> sections;

    public ListSection(List<String> sections) {
        this.sections = sections;
    }

    @Override
    public String toString() {
        return sections.toString();
    }
}
