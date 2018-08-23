package com.urise;

import com.urise.model.*;
import java.time.Month;
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

        resume.addSection(SectionType.PERSONAL, new TextSectionBody("Мужчина, 27 лет, родился 26 июля 1991"));
        resume.addSection(SectionType.OBJECTIVE, new TextSectionBody("Java junior"));
        resume.addSection(SectionType.ACHIEVEMENT, new ListSectionBody(Arrays.asList(
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike.",
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.")));
        resume.addSection(SectionType.QUALIFICATION, new ListSectionBody(Arrays.asList(
                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2")));
        Organization experience1 = new Organization(
                "RIT Center",
                YearMonth.of(2015, Month.FEBRUARY),
                YearMonth.of(2018, Month.APRIL),
                "Java архитектор",
                "Организация процесса разработки системы ERP для разных окружений: релизная политика, " +
                        "версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование " +
                        "системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы.");
        Organization experience2 = new Organization(
                "Wrike",
                YearMonth.of(2013, Month.FEBRUARY),
                YearMonth.of(2015, Month.APRIL),
                "Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                        "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                        "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");

        resume.addSection(SectionType.EXPERIENCE, new OrganizationSectionBody(Arrays.asList(experience1, experience2)));

        Organization education1 = new Organization(
                "Coursera",
                YearMonth.of(2011, Month.JULY),
                YearMonth.of(2011, Month.SEPTEMBER),
                "Functional Programming Principles in Scala", null);
        Organization education2 = new Organization(
                "Udemy",
                YearMonth.of(2011, Month.JULY),
                YearMonth.of(2011, Month.SEPTEMBER),
                "Functional Programming Principles in Scala", null);

        resume.addSection(SectionType.EDUCATION, new OrganizationSectionBody(Arrays.asList(education1, education2)));

        System.out.println(resume.toString());
    }
}
