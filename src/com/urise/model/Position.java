package com.urise.model;

import com.urise.utill.YearMonthAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.YearMonth;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Position implements Serializable {
    private final static long serialVersionUID = 1L;

    public static final Position EMPTY = new Position();

    @XmlJavaTypeAdapter(YearMonthAdapter.class)
    private YearMonth startDate;
    @XmlJavaTypeAdapter(YearMonthAdapter.class)
    private YearMonth endDate;
    private String title;
    private String description;

    public Position() {
    }

    public Position(YearMonth startDate, YearMonth endDate, String title, String description) {
        Objects.requireNonNull(startDate);
        Objects.requireNonNull(endDate);
        Objects.requireNonNull(title);
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
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
