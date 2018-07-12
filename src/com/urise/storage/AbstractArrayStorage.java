package com.urise.storage;

import com.urise.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 1000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        Integer indexResume = getIndexByUuid(uuid);
        if (indexResume != null) {
            return storage[indexResume];
        } else {
            System.out.println("ERROR: Resume does not exist in storage");
            return null;
        }
    }

    protected abstract Integer getIndexByUuid(String uuid);
}
