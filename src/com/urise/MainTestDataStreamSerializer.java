package com.urise;

import com.urise.model.*;
import com.urise.storage.serialization.DataStreamSerializer;
import com.urise.storage.serialization.Serializer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.YearMonth;
import java.util.Arrays;

public class MainTestDataStreamSerializer {

    protected final static Path FILE_STORAGE_DIR = Paths.get("E:\\basejava\\storage_test\\resume_file2");
    private static Serializer serializer = new DataStreamSerializer();

    public static void main(String[] args) {

        Resume resume = new Resume("Иванов Иван Иванович");

        resume.addContact(ContactType.TELEPHONE, "8 951-444-09-44");
        resume.addContact(ContactType.SKYPE, "aivanov");
        resume.addContact(ContactType.EMAIL, "aivanov@mail.ru");
        resume.addContact(ContactType.GITHUB, "https://github.com/Ivanov");
        resume.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/ivanov");
        resume.addContact(ContactType.STACKOVERFLOW, "https://ru.stackoverflow.com/Ivanov");
        resume.addContact(ContactType.HOMEPAGE, "https://ivanov.ru");

        resume.addSection(SectionType.PERSONAL, new TextSection("Мужчина, 27 лет, родился 26 июля 1991"));
        resume.addSection(SectionType.OBJECTIVE, new TextSection("Java junior"));
        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList(
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike.",
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.")));
        resume.addSection(SectionType.QUALIFICATION, new ListSection(Arrays.asList(
                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2")));
        Position position1 = new Position(
                YearMonth.parse("2015-12"), YearMonth.parse("2018-12"), "Java архитектор", null);
        Position position2 = new Position(
                YearMonth.parse("2012-10"), YearMonth.parse("2015-11"), "Старший разработчик (backend)", null);
        ExperienceEntry experience1 = new ExperienceEntry("RIT Center","https://rit.ru", Arrays.asList(position1, position2));

        Position position3 = new Position(
                YearMonth.parse("2015-12"), YearMonth.parse("2018-12"), "Младший разработчик", null);
        Position position4 = new Position(
                YearMonth.parse("2012-10"), YearMonth.parse("2015-11"), "Очень младший разработчик", null);
        ExperienceEntry experience2 = new ExperienceEntry("Wrike","https://wrike.ru", Arrays.asList(position3, position4));

        resume.addSection(SectionType.EXPERIENCE, new ExperienceSection(Arrays.asList(experience1, experience2)));

        Position education1 = new Position(
                YearMonth.parse("2015-12"), YearMonth.parse("2018-12"), "Курсы для продолжающих", null);
        Position education2 = new Position(
                YearMonth.parse("2012-10"), YearMonth.parse("2015-11"), "Курсы для начинающих", null);
        ExperienceEntry education = new ExperienceEntry("Udemy","https://udemy.ru", Arrays.asList(education1, education2));
        resume.addSection(SectionType.EDUCATION, new ExperienceSection(Arrays.asList(education)));

        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(Files.newOutputStream(FILE_STORAGE_DIR));
            serializer.doWrite(resume, bufferedOutputStream);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            Resume resumeFromFile = serializer.doRead(new BufferedInputStream(Files.newInputStream(FILE_STORAGE_DIR)));
            System.out.println(resumeFromFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
