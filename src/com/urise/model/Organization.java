package com.urise.model;

import java.time.YearMonth;
import java.util.Objects;

public class Organization {

    private Link name;
    private YearMonth startDate;
    private YearMonth endDate;
    private String title;
    private String description;

    public Organization(String name, String url, YearMonth startDate, YearMonth endDate, String title, String description) {
        Objects.requireNonNull(startDate);
        Objects.requireNonNull(endDate);
        Objects.requireNonNull(title);
        this.name = new Link(name, url);
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return name + "\n" +
                startDate.getMonthValue() + "/" + startDate.getYear() + " - " +
                endDate.getMonthValue() + "/" + endDate.getYear() + "\t" + title + "\n" +
                "\t\t\t\t" + description;
    }
}
