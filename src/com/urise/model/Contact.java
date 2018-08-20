package com.urise.model;

public class Contact {

    private ContactType contactType;
    private String contactBody;

    public Contact(ContactType contactType, String contactBody) {
        this.contactType = contactType;
        this.contactBody = contactBody;
    }

    public ContactType getContactType() {
        return contactType;
    }

    public String getContactBody() {
        return contactBody;
    }
}
