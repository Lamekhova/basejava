package com.urise.model;

import java.time.YearMonth;
import java.util.Objects;

/**
 * contains details on industry/educational experience
 * name - name of organization/educational center
 * startDate - start of learning/working
 */
public class ExperienceEntry {

    private Link name;
    private YearMonth startDate;
    private YearMonth endDate;
    private String title;
    private String description;

    public ExperienceEntry(String name, String url, YearMonth startDate, YearMonth endDate, String title, String description) {
        Objects.requireNonNull(startDate);
        Objects.requireNonNull(endDate);
        Objects.requireNonNull(title);
        this.name = new Link(name, url);
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    public Link getName() {
        return name;
    }

    public YearMonth getStartDate() {
        return startDate;
    }

    public YearMonth getEndDate() {
        return endDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name + "\n" +
                startDate.getMonthValue() + "/" + startDate.getYear() + " - " +
                endDate.getMonthValue() + "/" + endDate.getYear() + "\t" + title + "\n" +
                "\t\t\t\t" + description;
    }
}
