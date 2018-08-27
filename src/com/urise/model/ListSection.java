package com.urise.model;

import java.util.*;

public class ListSection implements Section {

    private List<String> sections;

    public ListSection(List<String> sections) {
        this.sections = sections;
    }

    @Override
    public String toString() {
        return sections.toString();
    }
}
