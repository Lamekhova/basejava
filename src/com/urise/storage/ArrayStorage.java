package com.urise.storage;

import com.urise.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage{

    public void save(Resume r) {
        if (size >= storage.length) {
            System.out.println("ERROR: Storage is full");
        }

        if (getIndexByUuid(r.getUuid()) != null) {
            System.out.println("ERROR: Resume has already been added in storage");
        } else {
            storage[size] = r;
            size++;
        }
    }

    // Return index of resume if it exists in storage
    protected Integer getIndexByUuid(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }
}
