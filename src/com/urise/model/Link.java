package com.urise.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Link implements Serializable {
    private final static long serialVersionUID = 1L;

    private String name;
    private String url;

    public Link() {
    }

    public Link(String name, String url) {
        Objects.requireNonNull(name);
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "\n" + name + " " + url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return Objects.equals(name, link.name) &&
                Objects.equals(url, link.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url);
    }
}
