package com.urise.model;

import java.util.Objects;

public class Link {

    private String name;
    private String url;

    public Link(String name, String url) {
        Objects.requireNonNull(name);
        this.name = name;
        this.url = url;
    }

    @Override
    public String toString() {
        return "\n" + name + " " + url;
    }
}
