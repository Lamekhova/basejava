package com.urise.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

public class OrganizationSectionBody implements SectionBody {

    private List<Organization> organizations;

    private Map<String, SortedSet<Map.Entry>> positions = new HashMap<>();

    public OrganizationSectionBody(List<Organization> organizations) {
        this.organizations = organizations;
    }

    @Override
    public String toString() {
        return organizations.toString();
    }
}
