package com.urise.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {
    private final static long serialVersionUID = 1L;

    private String uuid;
    private String fullName;

    private Map<ContactType, String> resumeContact = new EnumMap<>(ContactType.class);
    private Map<SectionType, Section> resumeSection = new EnumMap<>(SectionType.class);

    public Resume() {
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<ContactType, String> getResumeContact() {
        return resumeContact;
    }

    public Map<SectionType, Section> getResumeSection() {
        return resumeSection;
    }

    public void addSection(SectionType sectionType, Section section) {
        resumeSection.put(sectionType, section);
    }

    public void addContact(ContactType contactType, String contact) {
        resumeContact.put(contactType, contact);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(resumeContact, resume.resumeContact) &&
                Objects.equals(resumeSection, resume.resumeSection);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uuid, fullName, resumeContact, resumeSection);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(fullName);
        for (Map.Entry element : resumeContact.entrySet()){
            stringBuilder.append("\n" + element.getKey() + ": \n" + element.getValue() + "\n");
        }
        for (Map.Entry element : resumeSection.entrySet()){
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
