package com.urise;

import com.urise.model.Resume;
import com.urise.storage.SortedArrayStorage;
import com.urise.storage.Storage;
import java.util.ArrayList;
import java.util.List;

public class MainTestArrayStorage {

    private static final Storage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        final Resume r1 = new Resume("uuid1", "John Dorian");
        final Resume r2 = new Resume("uuid2", "Chris Turk");
        final Resume r3 = new Resume("uuid3", "Doctor Cox");
        final Resume r4 = new Resume("uuid4", "Elliot Reid");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r2);

        List list = new ArrayList();

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.update(r2);
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    private static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}
