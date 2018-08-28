package com.urise;

import com.urise.model.*;
import java.time.YearMonth;
import java.util.Arrays;

public class MainTestResume {

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
        ExperienceEntry experience1 = new ExperienceEntry("RIT Center",null, Arrays.asList(position1, position2));

        Position position3 = new Position(
                YearMonth.parse("2015-12"), YearMonth.parse("2018-12"), "Младший разработчик", null);
        Position position4 = new Position(
                YearMonth.parse("2012-10"), YearMonth.parse("2015-11"), "Очень младший разработчик", null);
        ExperienceEntry experience2 = new ExperienceEntry("Wrike",null, Arrays.asList(position3, position4));

        resume.addSection(SectionType.EXPERIENCE, new ExperienceSection(Arrays.asList(experience1, experience2)));

        Position education1 = new Position(
                YearMonth.parse("2015-12"), YearMonth.parse("2018-12"), "Курсы для продолжающих", null);
        Position education2 = new Position(
                YearMonth.parse("2012-10"), YearMonth.parse("2015-11"), "Курсы для начинающих", null);
        ExperienceEntry education = new ExperienceEntry("Udemy",null, Arrays.asList(education1, education2));
        resume.addSection(SectionType.EDUCATION, new ExperienceSection(Arrays.asList(education)));

        System.out.println(resume.toString());
    }
}
