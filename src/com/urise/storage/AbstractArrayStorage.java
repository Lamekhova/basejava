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

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        Integer indexResume = getIndexByUuid(r.getUuid());
        if (indexResume != null) {
            storage[indexResume] = r;
            System.out.println("Resume " + r.getUuid() + " has been updated");
        } else {
            System.out.println("ERROR: Resume does not exist in storage");
        }
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

    public void delete(String uuid) {
        Integer indexResume = getIndexByUuid(uuid);
        if (indexResume != null) {
            storage[indexResume] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("ERROR: Resume does not exist in storage");
        }
    }

    protected abstract Integer getIndexByUuid(String uuid);

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }
}
