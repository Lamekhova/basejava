package com.urise.model;

public class TextSection extends Section {
    private final static long serialVersionUID = 1L;

    private String sectionBody;

    public TextSection(String sectionBody) {
        this.sectionBody = sectionBody;
    }

    @Override
    public String toString() {
        return sectionBody;
    }
}
