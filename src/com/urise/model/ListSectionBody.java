package com.urise.model;

import java.util.*;

public class ListSectionBody implements SectionBody {

    private List<String> sections;

    public ListSectionBody(List<String> sections) {
        this.sections = sections;
    }

    @Override
    public String toString() {
        return sections.toString();
    }
}
