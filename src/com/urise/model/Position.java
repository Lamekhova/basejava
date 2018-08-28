package com.urise.model;

import java.time.YearMonth;
import java.util.Objects;

public class Position {

    private YearMonth startDate;
    private YearMonth endDate;
    private String title;
    private String description;

    public Position(YearMonth startDate, YearMonth endDate, String title, String description) {
        Objects.requireNonNull(startDate);
        Objects.requireNonNull(endDate);
        Objects.requireNonNull(title);
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(startDate, position.startDate) &&
                Objects.equals(endDate, position.endDate) &&
                Objects.equals(title, position.title) &&
                Objects.equals(description, position.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, title, description);
    }

    @Override
    public String toString() {
        return startDate + " - " + endDate + "\t" + title + "\n" + description + "\n";
    }
}
