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

    public void save(Resume r) {
        if (size >= storage.length) {
            System.out.println("ERROR: Storage is full");
        }
    }

    public void update(Resume r) {
        Integer indexResume = getIndexByUuid(r.getUuid());
        if (indexResume >= 0) {
            storage[indexResume] = r;
            System.out.println("Resume " + r.getUuid() + " has been updated");
        } else {
            System.out.println("ERROR: Resume does not exist in storage");
        }
    }

    public Resume get(String uuid) {
        Integer indexResume = getIndexByUuid(uuid);
        if (indexResume >= 0) {
            return storage[indexResume];
        } else {
            System.out.println("ERROR: Resume " + uuid + " does not exist in storage");
            return null;
        }
    }

    public abstract void delete(String uuid);

    protected abstract Integer getIndexByUuid(String uuid);

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }
}
