package com.urise.model;

import java.util.*;

public class ListSectionBody extends SectionBody {

    private List<String> sectionBody;

    public ListSectionBody(List<String> sections) {
        this.sectionBody = new ArrayList<>(sections);
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : sectionBody) {
            stringBuilder.append((prefix != null ? prefix : "") + string + "\n");
        }
        return stringBuilder.toString();
    }
}
