package com.urise.storage;

import com.urise.model.Resume;
import com.urise.storage.ArrayStorage;

/**
 * Test for com.urise.webapp.storage.com.urise.storage.ArrayStorage
 */
public class MainTestArrayStorage {
    private static final Storage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) {
        final Resume r1 = new Resume();
        r1.setUuid("uuid1");
        final Resume r2 = new Resume();
        r2.setUuid("uuid2");
        final Resume r3 = new Resume();
        r3.setUuid("uuid3");


        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());
        System.out.println("Get uuid3: " + ARRAY_STORAGE.get("uuid3"));

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.update(r2);
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}