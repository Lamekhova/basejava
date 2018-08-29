package com.urise.model;

import java.io.Serializable;
import java.util.Objects;

public class Link implements Serializable {
    private final static long serialVersionUID = 1L;

    private String name;
    private String url;

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
}
