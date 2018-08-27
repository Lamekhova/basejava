package com.urise.model;

public class TextSection implements Section {

    private String sectionBody;

    public TextSection(String sectionBody) {
        this.sectionBody = sectionBody;
    }

    @Override
    public String toString() {
        return sectionBody;
    }
}
