package com.urise.model;

import java.util.List;

public class OrganizationSectionBody implements SectionBody {

    private List<Organization> organizations;

    public OrganizationSectionBody(List<Organization> organizations) {
        this.organizations = organizations;
    }

    @Override
    public String toString() {
        return organizations.toString();
    }
}
