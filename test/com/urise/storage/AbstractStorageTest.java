package com.urise.storage;

import com.urise.exception.ExistStorageExeption;
import com.urise.exception.NotExistStorageExeption;
import com.urise.model.*;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.time.YearMonth;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class AbstractStorageTest {

    protected final static File STORAGE_DIR = new File("C:\\Users\\alamehova\\basejava\\storage_resume");

    protected Storage storage;

    protected static final String UUID_1 = "uuid1";
    protected static final Resume RESUME_1 = new Resume(UUID_1, "John Dorian");

    static {
        RESUME_1.addContact(ContactType.TELEPHONE, "8 951-444-09-44");
        RESUME_1.addContact(ContactType.SKYPE, "aivanov");
        RESUME_1.addContact(ContactType.EMAIL, "aivanov@mail.ru");
        RESUME_1.addContact(ContactType.GITHUB, "https://github.com/Ivanov");
        RESUME_1.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/ivanov");
        RESUME_1.addContact(ContactType.STACKOVERFLOW, "https://ru.stackoverflow.com/Ivanov");
        RESUME_1.addContact(ContactType.HOMEPAGE, "https://ivanov.ru");

        RESUME_1.addSection(SectionType.PERSONAL, new TextSection("Мужчина, 27 лет, родился 26 июля 1991"));
        RESUME_1.addSection(SectionType.OBJECTIVE, new TextSection("Java junior"));
        RESUME_1.addSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList(
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike.",
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.")));
        RESUME_1.addSection(SectionType.QUALIFICATION, new ListSection(Arrays.asList(
                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2")));

        Position position1 = new Position(
                YearMonth.parse("2015-12"), YearMonth.parse("2018-12"), "Java архитектор", null);
        Position position2 = new Position(
                YearMonth.parse("2012-10"), YearMonth.parse("2015-11"), "Старший разработчик (backend)", null);
        ExperienceEntry experience1 = new ExperienceEntry("RIT Center",null, Arrays.asList(position1, position2));

        Position position3 = new Position(
                YearMonth.parse("2015-12"), YearMonth.parse("2018-12"), "Младший разработчик", null);
        Position position4 = new Position(
                YearMonth.parse("2012-10"), YearMonth.parse("2015-11"), "Очень младший разработчик", null);
        ExperienceEntry experience2 = new ExperienceEntry("Wrike",null, Arrays.asList(position3, position4));

        RESUME_1.addSection(SectionType.EXPERIENCE, new ExperienceSection(Arrays.asList(experience1, experience2)));

        Position education1 = new Position(
                YearMonth.parse("2015-12"), YearMonth.parse("2018-12"), "Курсы для продолжающих", null);
        Position education2 = new Position(
                YearMonth.parse("2012-10"), YearMonth.parse("2015-11"), "Курсы для начинающих", null);
        ExperienceEntry education = new ExperienceEntry("Udemy",null, Arrays.asList(education1, education2));
        RESUME_1.addSection(SectionType.EDUCATION, new ExperienceSection(Arrays.asList(education)));

    }

    protected static final String UUID_2 = "uuid2";
    protected static final Resume RESUME_2 = new Resume(UUID_2, "Chris Turk");

    protected static final String UUID_3 = "uuid3";
    protected static final Resume RESUME_3 = new Resume(UUID_3, "Doctor Cox");

    protected static final String UUID_4 = "uuid4";
    protected static final Resume RESUME_4 = new Resume(UUID_4, "Elliot Reid");

    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_3);
        storage.save(RESUME_1);
        storage.save(RESUME_2);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertSize(4);
        assertEquals(RESUME_4, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageExeption.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_1, "NewName");
        storage.update(newResume);
        assertEquals(newResume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageExeption.class)
    public void updateNotExist() {
        Resume newResume = new Resume(UUID_4, "Fake");
        storage.update(newResume);
    }

    @Test
    public void get() {
        assertEquals(RESUME_1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageExeption.class)
    public void getNotExist() {
        storage.get(UUID_4);
    }

    @Test(expected = NotExistStorageExeption.class)
    public void delete() {
        storage.delete(UUID_1);
        assertSize(2);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageExeption.class)
    public void deleteNotExist() {
        storage.delete(UUID_4);
        assertSize(3);
    }

    @Test
    public void getAllSorted() {
        assertTrue(storage.getAllSorted().containsAll(Arrays.asList(RESUME_1, RESUME_2, RESUME_3)));
        assertSize(3);
    }

    void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}