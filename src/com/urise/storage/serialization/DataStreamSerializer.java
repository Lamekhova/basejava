package com.urise.storage.serialization;

import com.urise.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
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
            dataOutputStream.writeInt(resume.getResumeSection().size());
            for (Map.Entry<SectionType, Section> entry : resume.getResumeSection().entrySet()) {
                SectionType sectionType = entry.getKey();
                Section section = entry.getValue();
                dataOutputStream.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dataOutputStream.writeUTF(((TextSection) section).getSectionBody());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        writeListSection(dataOutputStream, (ListSection) section);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeExperienceSection(dataOutputStream, (ExperienceSection) section);
                        break;
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
            int sizeContacts = dataInputStream.readInt();
            for (int i = 0; i < sizeContacts; i++) {
                ContactType contactType = ContactType.valueOf(dataInputStream.readUTF());
                String value = dataInputStream.readUTF();
                resume.addContact(contactType, value);
            }
            int sizeSections = dataInputStream.readInt();
            for (int i = 0; i < sizeSections; i++) {
                SectionType sectionType = SectionType.valueOf(dataInputStream.readUTF());
                resume.addSection(sectionType, readSection(dataInputStream, sectionType));
            }
            return resume;
        }
    }

    private void writeListSection(DataOutputStream dataOutputStream, ListSection listSection) throws IOException {
        dataOutputStream.writeInt(listSection.getContent().size());
        for (String entry : listSection) {
            dataOutputStream.writeUTF(entry);
        }
    }

    public void writeExperienceSection(DataOutputStream dataOutputStream, ExperienceSection experienceSection) throws IOException {
        dataOutputStream.writeInt(experienceSection.getExperienceEntries().size());
        for (ExperienceEntry experienceEntry : experienceSection.getExperienceEntries()) {
            dataOutputStream.writeUTF(experienceEntry.getName().getName());
            if (experienceEntry.getName().getUrl() != null) {
                dataOutputStream.writeUTF(experienceEntry.getName().getUrl());
            } else {
                dataOutputStream.writeUTF("\n");
            }
            dataOutputStream.writeInt(experienceEntry.getPositionsList().size());
            for (Position positions : experienceEntry.getPositionsList()) {
                dataOutputStream.writeUTF(positions.getStartDate().toString());
                dataOutputStream.writeUTF(positions.getEndDate().toString());
                dataOutputStream.writeUTF(positions.getTitle());
                String descriptionToWrite = positions.getDescription() != null ? positions.getDescription() : "\n";
                dataOutputStream.writeUTF(descriptionToWrite);
            }
        }
    }

    private Section readSection(DataInputStream dataInputStream, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                return new TextSection(dataInputStream.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATION:
                return new ListSection(readListSection(dataInputStream));
            case EXPERIENCE:
            case EDUCATION:
                return new ExperienceSection(readExperienceSection(dataInputStream));
            default:
                throw new IllegalStateException();
        }
    }

    private List<String> readListSection(DataInputStream dataInputStream) throws IOException {
        int numberOfSections = dataInputStream.readInt();
        List<String> listOfString = new ArrayList<>();
        for (int sectionNumber = 0; sectionNumber < numberOfSections; sectionNumber++) {
            listOfString.add(dataInputStream.readUTF());
        }
        return listOfString;
    }

    private List<ExperienceEntry> readExperienceSection(DataInputStream dataInputStream) throws IOException {
        int numberOfExperienceEntries = dataInputStream.readInt();
        List<ExperienceEntry> experienceEntryList = new ArrayList<>();
        for (int experienceEntryNumber = 0; experienceEntryNumber < numberOfExperienceEntries; experienceEntryNumber++) {
            String name = dataInputStream.readUTF();
            String url = dataInputStream.readUTF();
            url = url.equals("\n") ? null : url;
            List<Position> listOfPositions = new ArrayList<>();
            ExperienceEntry experienceEntry = new ExperienceEntry(name, url, listOfPositions);
            int numberOfPositions = dataInputStream.readInt();
            for (int positionNumber = 0; positionNumber < numberOfPositions; positionNumber++) {
                YearMonth startDate = YearMonth.parse(dataInputStream.readUTF());
                YearMonth endDate = YearMonth.parse(dataInputStream.readUTF());
                String title = dataInputStream.readUTF();
                String description = dataInputStream.readUTF();
                description = description.equals("\n") ? null : description;
                Position position = new Position(startDate, endDate, title, description);
                listOfPositions.add(position);
            }
            experienceEntryList.add(experienceEntry);
        }
        return experienceEntryList;
    }
}
