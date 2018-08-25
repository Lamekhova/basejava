package com.urise.model;

public enum ContactType {

    TELEPHONE("Номер телефона"),
    SKYPE("Скайп"),
    EMAIL("Электронная почта"),
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль Stackoverflow"),
    HOMEPAGE("Домашняя страница");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

//    @Override
//    public String toString() {
//        return title;
//    }
}
