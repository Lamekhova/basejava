package com.urise;

import com.urise.model.*;

import java.util.Arrays;

public class MainTestResume {

    public static void main(String[] args) {

        Resume resume = new Resume("Иванов Иван Иванович");

        Contact telephone = new Contact(ContactType.TELEPHONE, "8 951 444 09 44");
        Contact skype = new Contact(ContactType.SKYPE, "aivanov");
        Contact eMail = new Contact(ContactType.EMAIL, "aivanov@mail.ru");
        Contact gitHub = new Contact(ContactType.GITHUB, "https://github.com/Ivanov");
        Contact linkedIn = new Contact(ContactType.LINKEDIN, "https://www.linkedin.com/in/ivanov");
        Contact stackOverflow = new Contact(ContactType.STACKOVERFLOW, "https://ru.stackoverflow.com/Ivanov");
        Contact homePage = new Contact(ContactType.HOMEPAGE, "https://ivanov.ru");

        resume.addContact(telephone);
        resume.addContact(skype);
        resume.addContact(eMail);
        resume.addContact(gitHub);
        resume.addContact(linkedIn);
        resume.addContact(stackOverflow);
        resume.addContact(homePage);

        Section personal = new Section(SectionType.PERSONAL, "Мужчина, 27 лет, родился 26 июля 1991");
        Section objective = new Section(SectionType.OBJECTIVE, "Java junior");
//        Section achievement = new Section(SectionType.ACHIEVEMENT,
//                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
//                            "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        Section achievement = new Section(SectionType.ACHIEVEMENT, new ListSectionBody(Arrays.asList(
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike.",
                            "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.")));
        Section qualification = new Section(SectionType.QUALIFICATION,
                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        Section experience = new Section(SectionType.EXPERIENCE,
                "Ведущий программист " +
                        "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, SmartGWT, GWT, Jasper, Oracle).");
        Section education = new Section(SectionType.EDUCATION,
                "03/2013 - 05/2013\t\"Functional Programming Principles in Scala\" by Martin Odersky");

        resume.addSection(personal);
        resume.addSection(objective);
        resume.addSection(achievement);
        resume.addSection(qualification);
        resume.addSection(experience);
        resume.addSection(education);

        System.out.println(resume.toString());
    }
}
