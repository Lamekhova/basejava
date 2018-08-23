package com.urise.model;

public class TextSectionBody extends SectionBody {

    private String sectionBody;

    public TextSectionBody(String sectionBody) {
        this.sectionBody = sectionBody;
    }

    @Override
    public String toString() {
        return (this.prefix != null ? this.prefix : "") + sectionBody + "\n";
    }

}
