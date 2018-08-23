package com.urise.model;

import java.util.List;

public class OrganizationSectionBody extends SectionBody {

    private List<Organization> organizations;

    public OrganizationSectionBody(List<Organization> organizations) {
        this.organizations = organizations;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Organization element : organizations) {
            stringBuilder.append((prefix != null ? prefix : "") + element + "\n");
        }
        return stringBuilder.toString();
    }
}
