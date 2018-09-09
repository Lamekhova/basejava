package com.urise.storage.serialization;

import com.urise.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.*;
import java.util.Map;

/**
 * save and restore file in DataStreamFormat
 */
public class DataStreamSerializer implements Serializer {

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
                if (entry.getKey().equals(SectionType.PERSONAL) || entry.getKey().equals(SectionType.OBJECTIVE)) {
                    dataOutputStream.writeUTF(entry.getKey().name());
                    dataOutputStream.writeUTF(entry.getValue().toString());
                } else if (entry.getKey().equals(SectionType.ACHIEVEMENT) || entry.getKey().equals(SectionType.QUALIFICATION)) {
                    dataOutputStream.writeUTF(entry.getKey().name());
                    ListSection listSection = (ListSection) entry.getValue();
                    dataOutputStream.writeInt(listSection.getSections().size());
                    for (String section : listSection) {
                        dataOutputStream.writeUTF(section);
                    }
                } else if ((entry.getKey().equals(SectionType.EXPERIENCE) || entry.getKey().equals(SectionType.EDUCATION))) {
                    dataOutputStream.writeUTF(entry.getKey().name());
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
                System.out.println(sectionTypeFromFile);
                if (SectionType.valueOf(sectionTypeFromFile).equals(SectionType.PERSONAL)) {
                    resume.addSection(SectionType.PERSONAL, new TextSection(dataInputStream.readUTF()));
                } else if (SectionType.valueOf(sectionTypeFromFile).equals(SectionType.OBJECTIVE)) {
                    resume.addSection(SectionType.OBJECTIVE, new TextSection(dataInputStream.readUTF()));
                } else if (SectionType.valueOf(sectionTypeFromFile).equals(SectionType.ACHIEVEMENT)) {
                    int numberOfSections = dataInputStream.readInt();
                    ListSection listSection = new ListSection();
                    for (int sectionNumber = 0; sectionNumber < numberOfSections; sectionNumber++) {
                        listSection.getSections().add(dataInputStream.readUTF());
                    }
                    resume.addSection(SectionType.ACHIEVEMENT, listSection);

                } else if (SectionType.valueOf(sectionTypeFromFile).equals(SectionType.QUALIFICATION)) {
                    int numberOfSections = dataInputStream.readInt();
                    ListSection listSection = new ListSection();
                    for (int sectionNumber = 0; sectionNumber < numberOfSections; sectionNumber++) {
                        listSection.getSections().add(dataInputStream.readUTF());
                    }
                    resume.addSection(SectionType.QUALIFICATION, listSection);

                } else if (SectionType.valueOf(sectionTypeFromFile).equals(SectionType.EXPERIENCE)) {
                    int numberOfExperienceEntries = dataInputStream.readInt();
                    List<ExperienceEntry> experienceEntryList = new ArrayList<>();
                    for (int experienceEntryNumber = 0; experienceEntryNumber < numberOfExperienceEntries; experienceEntryNumber++) {
                        String name = dataInputStream.readUTF();
                        String url = dataInputStream.readUTF();
                        url = url.equals("null") ?  null : url;
                        List<Position> listOfPositions = new ArrayList<>();
                        ExperienceEntry experienceEntry = new ExperienceEntry(name, url, listOfPositions);
                        int numberOfPositions = dataInputStream.readInt();
                        for (int positionNumber = 0; positionNumber < numberOfPositions; positionNumber++) {
                            YearMonth startDate = YearMonth.parse(dataInputStream.readUTF());
                            YearMonth endDate = YearMonth.parse(dataInputStream.readUTF());
                            String title = dataInputStream.readUTF();
                            String description = dataInputStream.readUTF();
                            description = description.equals("null") ?  null : description;
                            Position position = new Position(startDate, endDate, title, description);
                            listOfPositions.add(position);
                        }
                        experienceEntryList.add(experienceEntry);
                    }
                    resume.addSection(SectionType.EXPERIENCE, new ExperienceSection(experienceEntryList));
                } else if ((SectionType.valueOf(sectionTypeFromFile).equals(SectionType.EDUCATION))) {
                    int numberOfExperienceEntries = dataInputStream.readInt();
                    List<ExperienceEntry> experienceEntryList = new ArrayList<>();
                    for (int experienceEntryNumber = 0; experienceEntryNumber < numberOfExperienceEntries; experienceEntryNumber++) {
                        String name = dataInputStream.readUTF();
                        String url = dataInputStream.readUTF();
                        url = url.equals("null") ?  null : url;
                        List<Position> listOfPositions = new ArrayList<>();
                        ExperienceEntry experienceEntry = new ExperienceEntry(name, url, listOfPositions);
                        int numberOfPositions = dataInputStream.readInt();
                        for (int positionNumber = 0; positionNumber < numberOfPositions; positionNumber++) {
                            YearMonth startDate = YearMonth.parse(dataInputStream.readUTF());
                            YearMonth endDate = YearMonth.parse(dataInputStream.readUTF());
                            String title = dataInputStream.readUTF();
                            String description = dataInputStream.readUTF();
                            description = description.equals("null") ?  null : description;
                            Position position = new Position(startDate, endDate, title, description);
                            listOfPositions.add(position);
                        }
                        experienceEntryList.add(experienceEntry);
                }
                    resume.addSection(SectionType.EDUCATION, new ExperienceSection(experienceEntryList));
                    break;
                }
            }
                return resume;
        }
    }
}
