package com.urise.model;

import java.time.YearMonth;
import java.util.Objects;

public class Organization {

    protected String name;
    private YearMonth startDate;
    private YearMonth endDate;
    private String title;
    private String description;

    public Organization(String organizationName, YearMonth startDate, YearMonth endDate, String title, String description) {
        Objects.requireNonNull(organizationName);
        Objects.requireNonNull(startDate);
        Objects.requireNonNull(endDate);
        Objects.requireNonNull(title);

        this.name = organizationName;
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
