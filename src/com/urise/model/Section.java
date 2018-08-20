package com.urise.model;

public class Section {

    private SectionType sectionType;
    private SectionBody sectionBody;

    public Section(SectionType sectionType, String sectionBody) {
        this.sectionType = sectionType;
        this.sectionBody = new SimpleSectionBody(sectionBody);
    }

    public Section(SectionType sectionType, SectionBody sectionBody) {
        this.sectionType = sectionType;
        this.sectionBody = sectionBody;
    }

    public SectionType getSectionType() {
        return sectionType;
    }

    public SectionBody getSectionBody() {
        return sectionBody;
    }

    @Override
    public String toString() {
        return sectionType.toString() + ":\n" + sectionBody.toString();
    }

}
