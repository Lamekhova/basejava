package com.urise.model;

import java.util.*;

public class Resume implements Comparable<Resume> {

    private final String uuid;
    private String fullName;

    private static  final List<SectionType> ORDER_OF_SECTION_TYPES = Arrays.asList(SectionType.PERSONAL, SectionType.OBJECTIVE,
            SectionType.ACHIEVEMENT, SectionType.QUALIFICATION, SectionType.EXPERIENCE, SectionType.EDUCATION);
    private static  final List<ContactType> ORDER_OF_CONTACT_TYPES = Arrays.asList(ContactType.TELEPHONE, ContactType.SKYPE,
            ContactType.EMAIL, ContactType.LINKEDIN, ContactType.GITHUB, ContactType.STACKOVERFLOW,
            ContactType.HOMEPAGE);

    private Map<SectionType, Section> resumeSection = new HashMap<>();
    private Map<ContactType, String> resumeContact = new HashMap<>();

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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(fullName + "\n\n");

        for (ContactType element : ORDER_OF_CONTACT_TYPES) {
            String contact = resumeContact.get(element);
            if (contact != null) {
                stringBuilder.append(element.getTitle() + "\t" + contact + "\n");
            }
        }

        stringBuilder.append("\n");

        for (SectionType element : ORDER_OF_SECTION_TYPES) {
            Section section = resumeSection.get(element);
            if (section != null) {
                section.getSectionBody().setPrefix("\t");
                stringBuilder.append(element.getTitle() + "\n" + section.getSectionBody() + "\n");
            }
        }

        return stringBuilder.toString();
    }

    @Override
    public int compareTo(Resume o) {
        int result = fullName.compareTo(o.fullName);
        return result != 0 ? result : uuid.compareTo(o.uuid);
    }

    public void addSection(Section section) {
        resumeSection.put(section.getSectionType(), section);
    }

    public void addContact(Contact contact) {
        resumeContact.put(contact.getContactType(), contact.getContactBody());
    }
}
