package com.urise.model;

import java.util.*;

public class ListSectionBody extends SectionBody {

    private List<String> stringList;
    private String prefix;

    public ListSectionBody(List<String> sections) {
        this.stringList = new ArrayList<>(sections);
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (String string : stringList) {
            stringBuffer.append((prefix != null ? prefix : "") + string + "\n");
        }
        return stringBuffer.toString();
    }
}
