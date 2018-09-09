package com.urise.storage.serialization;

import com.urise.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.*;
import java.util.Map;
import java.util.function.Predicate;

/**
 * save and restore file in DataStreamFormat
 */
public class DataStreamSerializer implements Serializer {

    private Predicate<String> isPersonalSectionType = s -> SectionType.valueOf(s).equals(SectionType.PERSONAL);
    private Predicate<String> isObjectiveSectionType = s -> SectionType.valueOf(s).equals(SectionType.OBJECTIVE);
    private Predicate<String> isAchievementSectionType = s -> SectionType.valueOf(s).equals(SectionType.ACHIEVEMENT);
    private Predicate<String> isQualificationSectionType = s -> SectionType.valueOf(s).equals(SectionType.QUALIFICATION);
    private Predicate<String> isExperienceSectionType = s -> SectionType.valueOf(s).equals(SectionType.EXPERIENCE);
    private Predicate<String> isEducationSectionType = s -> SectionType.valueOf(s).equals(SectionType.EDUCATION);

    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            dataOutputStream.writeUTF(resume.getUuid());
            dataOutputStream.writeUTF(resume.getFullName());
            dataOutputStream.writeInt(resume.getResumeContact().size());
            for (Map.Entry<ContactType, String> entry : resume.getResumeContact().entrySet()) {
                dataOutputStream.writeUTF(entry.getKey().name());
                dataOutputStream.writeUTF(entry.getValue());
            }
            for (Map.Entry<SectionType, Section> entry : resume.getResumeSection().entrySet()) {
                String entryName = entry.getKey().name();
                if (isPersonalSectionType.test(entryName) || isObjectiveSectionType.test(entryName)) {
                    dataOutputStream.writeUTF(entryName);
                    dataOutputStream.writeUTF(entry.getValue().toString());
                } else if (isAchievementSectionType.test(entryName) || isQualificationSectionType.test(entryName)) {
                    dataOutputStream.writeUTF(entryName);
                    ListSection listSection = (ListSection) entry.getValue();
                    dataOutputStream.writeInt(listSection.getSections().size());
                    for (String section : listSection) {
                        dataOutputStream.writeUTF(section);
                    }
                } else if ((isExperienceSectionType.test(entryName) || isEducationSectionType.test(entryName))) {
                    dataOutputStream.writeUTF(entryName);
                    ExperienceSection experienceSection = (ExperienceSection) entry.getValue();
                    dataOutputStream.writeInt(experienceSection.getExperienceEntries().size());
                    for (ExperienceEntry experienceEntry : experienceSection.getExperienceEntries()) {
                        dataOutputStream.writeUTF(experienceEntry.getName().getName());
                        if (experienceEntry.getName().getUrl() != null) {
                            dataOutputStream.writeUTF(experienceEntry.getName().getUrl());
                        } else {
                            dataOutputStream.writeUTF("null");
                        }
                        dataOutputStream.writeInt(experienceEntry.getPositionsList().size());
                        for (Position positions : experienceEntry.getPositionsList()) {
                            dataOutputStream.writeUTF(positions.getStartDate().toString());
                            dataOutputStream.writeUTF(positions.getEndDate().toString());
                            dataOutputStream.writeUTF(positions.getTitle());
                            String descriptionToWrite = positions.getDescription() != null ? positions.getDescription() : "null";
                            dataOutputStream.writeUTF(descriptionToWrite);
                        }
                    }
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(inputStream)) {
            String uuid = dataInputStream.readUTF();
            String fullName = dataInputStream.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dataInputStream.readInt();
            for (int i = 0; i < size; i++) {
                ContactType contactType = ContactType.valueOf(dataInputStream.readUTF());
                String value = dataInputStream.readUTF();
                resume.addContact(contactType, value);
            }
            while (true) {
                String sectionTypeFromFile = dataInputStream.readUTF();
                if (isPersonalSectionType.test(sectionTypeFromFile)) {
                    resume.addSection(SectionType.PERSONAL, new TextSection(dataInputStream.readUTF()));
                } else if (isObjectiveSectionType.test(sectionTypeFromFile)) {
                    resume.addSection(SectionType.OBJECTIVE, new TextSection(dataInputStream.readUTF()));
                } else if (isAchievementSectionType.test(sectionTypeFromFile)) {
                    resume.addSection(SectionType.ACHIEVEMENT, readListSection(dataInputStream));
                } else if (isQualificationSectionType.test(sectionTypeFromFile)) {
                    resume.addSection(SectionType.QUALIFICATION, readListSection(dataInputStream));
                } else if (isExperienceSectionType.test(sectionTypeFromFile)) {
                    resume.addSection(SectionType.EXPERIENCE, new ExperienceSection(readMapSection(dataInputStream)));
                } else if (isEducationSectionType.test(sectionTypeFromFile)) {
                    resume.addSection(SectionType.EDUCATION, new ExperienceSection(readMapSection(dataInputStream)));
                    break;
                }
            }
            return resume;
        }
    }

    public ListSection readListSection(DataInputStream dataInputStream) throws IOException {
        int numberOfSections = dataInputStream.readInt();
        ListSection listSection = new ListSection();
        for (int sectionNumber = 0; sectionNumber < numberOfSections; sectionNumber++) {
            listSection.getSections().add(dataInputStream.readUTF());
        }
        return listSection;
    }

    public List<ExperienceEntry> readMapSection(DataInputStream dataInputStream) throws IOException {
        int numberOfExperienceEntries = dataInputStream.readInt();
        List<ExperienceEntry> experienceEntryList = new ArrayList<>();
        for (int experienceEntryNumber = 0; experienceEntryNumber < numberOfExperienceEntries; experienceEntryNumber++) {
            String name = dataInputStream.readUTF();
            String url = dataInputStream.readUTF();
            url = url.equals("null") ? null : url;
            List<Position> listOfPositions = new ArrayList<>();
            ExperienceEntry experienceEntry = new ExperienceEntry(name, url, listOfPositions);
            int numberOfPositions = dataInputStream.readInt();
            for (int positionNumber = 0; positionNumber < numberOfPositions; positionNumber++) {
                YearMonth startDate = YearMonth.parse(dataInputStream.readUTF());
                YearMonth endDate = YearMonth.parse(dataInputStream.readUTF());
                String title = dataInputStream.readUTF();
                String description = dataInputStream.readUTF();
                description = description.equals("null") ? null : description;
                Position position = new Position(startDate, endDate, title, description);
                listOfPositions.add(position);
            }
            experienceEntryList.add(experienceEntry);
        }
        return experienceEntryList;
    }
}
