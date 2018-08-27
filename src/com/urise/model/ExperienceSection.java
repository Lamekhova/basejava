package com.urise.model;

import java.time.Month;
import java.time.YearMonth;
import java.util.*;

public class ExperienceSection implements Section {

    class ExperienceEntryComparator implements Comparator<ExperienceEntry> {

        @Override
        public int compare(ExperienceEntry o1, ExperienceEntry o2) {
            return o2.getStartDate().compareTo(o1.getStartDate());
        }
    }

    private Map<String, Set<ExperienceEntry>> experienceMap;

    public ExperienceSection(List<ExperienceEntry> experienceEntries) {
        this.experienceMap = new HashMap<>();
        for (ExperienceEntry element : experienceEntries) {
            String name = element.getName().getName();
            if (!experienceMap.containsKey(name)) {
                experienceMap.put(name, new TreeSet<>(new ExperienceEntryComparator()));
            }
            experienceMap.get(name).add(element);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Set<ExperienceEntry>> element : experienceMap.entrySet()) {
            Set<ExperienceEntry> setExpEntry = element.getValue();
            for (ExperienceEntry expEntry : setExpEntry) {
                stringBuilder.append(element.getKey() + "\n"+ expEntry.getStartDate()+ "/" + expEntry.getEndDate() +
                        "\t" + expEntry.getTitle() + "\n" +
                        "\t\t\t\t" + expEntry.getDescription());
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        ExperienceEntry experience1 = new ExperienceEntry(
                "RIT Center", null,
                YearMonth.of(2015, Month.FEBRUARY),
                YearMonth.of(2018, Month.APRIL),
                "Java архитектор",
                "Организация процесса разработки системы ERP для разных окружений: релизная политика, " +
                        "версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование " +
                        "системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы.");
        ExperienceEntry experience2 = new ExperienceEntry(
                "Wrike", null,
                YearMonth.of(2013, Month.FEBRUARY),
                YearMonth.of(2015, Month.APRIL),
                "Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                        "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                        "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        ExperienceEntry experience3 = new ExperienceEntry(
                "Wrike", null,
                YearMonth.of(2011, Month.FEBRUARY),
                YearMonth.of(2013, Month.APRIL),
                "Ручной гомик",
                "клей момент");
        ExperienceSection experienceSection = new ExperienceSection(Arrays.asList(experience1, experience2, experience3));
        System.out.println();
    }
}
