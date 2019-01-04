package com.urise.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {
    private final static long serialVersionUID = 1L;

    private String uuid;
    private String fullName;

    private Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public static final Resume EMPTY_RESUME = new Resume();

    static {
        EMPTY_RESUME.setSection(SectionType.OBJECTIVE, new TextSection(""));
        EMPTY_RESUME.setSection(SectionType.PERSONAL, new TextSection(""));
        EMPTY_RESUME.setSection(SectionType.ACHIEVEMENT, new ListSection(Collections.emptyList()));
        EMPTY_RESUME.setSection(SectionType.QUALIFICATION, new ListSection(Collections.emptyList()));
        EMPTY_RESUME.setSection(SectionType.EXPERIENCE, new ExperienceSection(Arrays.asList(ExperienceEntry.EMPTY)));
        EMPTY_RESUME.setSection(SectionType.EDUCATION, new ExperienceSection(Arrays.asList(ExperienceEntry.EMPTY)));
    }

    private void initResumeSections() {
        this.setSection(SectionType.OBJECTIVE, new TextSection(""));
        this.setSection(SectionType.PERSONAL, new TextSection(""));
        this.setSection(SectionType.ACHIEVEMENT, new ListSection(Collections.emptyList()));
        this.setSection(SectionType.QUALIFICATION, new ListSection(Collections.emptyList()));
        this.setSection(SectionType.EXPERIENCE, new ExperienceSection(Arrays.asList(ExperienceEntry.EMPTY)));
        this.setSection(SectionType.EDUCATION, new ExperienceSection(Arrays.asList(ExperienceEntry.EMPTY)));
    }

    public Resume() {
        initResumeSections();
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
        initResumeSections();
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
        initResumeSections();
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Map<ContactType, String> getResumeContact() {
        return contacts;
    }

    public Map<SectionType, Section> getResumeSection() {
        return sections;
    }

    public void addSection(SectionType sectionType, Section section) {
        sections.put(sectionType, section);
    }

    public void addContact(ContactType contactType, String contact) {
        contacts.put(contactType, contact);
    }

    public String getContact(ContactType type) {
        return contacts.get(type);
    }

    public Section getSection(SectionType type) {
        return sections.get(type);
    }

    public void setContact(ContactType type, String value) {
        contacts.put(type, value);
    }

    public void setSection(SectionType type, Section section) {
        sections.put(type, section);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(contacts, resume.contacts) &&
                Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(fullName);
        for (Map.Entry element : contacts.entrySet()){
            stringBuilder.append("\n" + element.getKey() + ": \n" + element.getValue() + "\n");
        }
        for (Map.Entry element : sections.entrySet()){
            stringBuilder.append("\n" + element.getKey() + ": \n" + element.getValue());
        }
        return stringBuilder.toString();
    }

    @Override
    public int compareTo(Resume o) {
        int result = fullName.compareTo(o.fullName);
        return result != 0 ? result : uuid.compareTo(o.uuid);
    }
}
