package com.urise.model;

import java.util.*;

public class Resume implements Comparable<Resume> {

    private final String uuid;
    private String fullName;

    private Map<ContactType, String> resumeContact = new EnumMap<>(ContactType.class);
    private Map<SectionType, SectionBody> resumeSection = new EnumMap<>(SectionType.class);

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(fullName);
        for (Map.Entry element : resumeContact.entrySet()){
            stringBuilder.append("\n" + element.getKey() + ": " + element.getValue());
        }
        for (Map.Entry element : resumeSection.entrySet()){
            stringBuilder.append("\n" + element.getKey() + ": " + element.getValue());
        }
        return stringBuilder.toString();
    }

    @Override
    public int compareTo(Resume o) {
        int result = fullName.compareTo(o.fullName);
        return result != 0 ? result : uuid.compareTo(o.uuid);
    }

    public void addSection(SectionType sectionType, SectionBody sectionBody) {
        resumeSection.put(sectionType, sectionBody);
    }

    public void addContact(ContactType contactType, String contact) {
        resumeContact.put(contactType, contact);
    }
}
