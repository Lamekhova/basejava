package com.urise.model;

import java.util.*;

public class ListSection extends Section implements Iterable<String> {
    private final static long serialVersionUID = 1L;

    private List<String> content;

    public ListSection() {
        this.content = new ArrayList<>();
    }

    public ListSection(String... strings) {
        this(Arrays.asList(strings));
    }

    public ListSection(List<String> content) {
        this.content = content;
    }

    public List<String> getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {

        return Objects.hash(content);
    }

    @Override
    public String toString() {
        return content.toString();
    }

    @Override
    public Iterator<String> iterator() {
        return this.content.iterator();
    }
}
