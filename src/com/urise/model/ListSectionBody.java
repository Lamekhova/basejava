package com.urise.model;

import java.util.*;

public class ListSectionBody extends SectionBody {

    private List<String> sectionBody;

    public ListSectionBody(List<String> sections) {
        this.sectionBody = new ArrayList<>(sections);
    }

    @Override
    public String toString() {
        return sectionBody.toString();
    }
}
