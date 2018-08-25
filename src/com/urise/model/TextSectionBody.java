package com.urise.model;

public class TextSectionBody implements SectionBody {

    private String sectionBody;

    public TextSectionBody(String sectionBody) {
        this.sectionBody = sectionBody;
    }

    @Override
    public String toString() {
        return sectionBody;
    }
}
