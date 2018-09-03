package com.urise.model;

import java.util.Objects;

public class TextSection extends Section {
    private final static long serialVersionUID = 1L;

    private String sectionBody;

    public TextSection() {
    }

    public TextSection(String sectionBody) {
        this.sectionBody = sectionBody;
    }

    @Override
    public String toString() {
        return sectionBody;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return Objects.equals(sectionBody, that.sectionBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionBody);
    }
}
