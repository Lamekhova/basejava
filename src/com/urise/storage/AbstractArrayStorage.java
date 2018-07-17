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
        System.out.println("Storage was cleaned");
    }

    public void save(Resume r) {
        if (size >= storage.length) {
            System.out.println("ERROR: Storage is full");
        }
        if (getIndexByUuid(r.getUuid()) > -1) {
            System.out.println("ERROR: Resume " + r.getUuid() + " has already been added in storage");
        } else {
            insertElement(r);
            size++;
            System.out.println("Resume " + r.getUuid() + " was added in storage");
        }
    }

    public void update(Resume r) {
        Integer indexResume = getIndexByUuid(r.getUuid());
        if (indexResume > -1) {
            storage[indexResume] = r;
            System.out.println("Resume " + r.getUuid() + " has been updated");
        } else {
            System.out.println("ERROR: Resume " + r.getUuid() + "does not exist in storage");
        }
    }

    public Resume get(String uuid) {
        Integer indexResume = getIndexByUuid(uuid);
        if (indexResume > -1) {
            return storage[indexResume];
        } else {
            System.out.println("ERROR: Resume " + uuid + " does not exist in storage");
            return null;
        }
    }

    @Override
    public void delete(String uuid) {
        int indexResume = getIndexByUuid(uuid);
        if (indexResume > -1) {
            shiftElement(indexResume);
            size--;
            System.out.println("ERROR: Resume " + uuid + " has been deleted");
        } else {
            System.out.println("ERROR: Resume " + uuid + " does not exist in storage");
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    // Return index of resume if it exists in storage
    protected abstract Integer getIndexByUuid(String uuid);

    public abstract void insertElement(Resume r);

    public abstract void shiftElement(int indexResume);
}
