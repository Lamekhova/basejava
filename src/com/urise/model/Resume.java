package com.urise.model;

import java.util.*;

public class Resume implements Comparable<Resume> {

    private final String uuid;
    private String fullName;

    private static  final List<ContactType> ORDER_OF_CONTACT_TYPES = Arrays.asList(ContactType.class.getEnumConstants());
    private static  final List<SectionType> ORDER_OF_SECTION_TYPES = Arrays.asList(SectionType.class.getEnumConstants());

    private Map<SectionType, SectionBody> resumeSection = new HashMap<>();
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
            SectionBody sectionBody = resumeSection.get(element);
            if (sectionBody != null) {
                sectionBody.setPrefix("\t");
                stringBuilder.append(element.getTitle() + "\n" + sectionBody + "\n");
            }
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
