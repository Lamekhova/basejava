package com.urise.model;

public class SimpleSectionBody extends SectionBody {

    private String sectionBody;

    public SimpleSectionBody(String sectionBody) {
        this.sectionBody = sectionBody;
    }

    @Override
    public String toString() {
        return (this.prefix != null ? this.prefix : "") + sectionBody;
    }

}
