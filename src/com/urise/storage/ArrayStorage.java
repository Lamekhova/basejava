package com.urise.storage;

import com.urise.model.Resume;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage{

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (size < storage.length) {
            System.out.println("ERROR: Storage is full");
        } else if (getIndexByUuid(r.getUuid()) != null) {
            System.out.println("ERROR: Resume has already been added in storage");
        } else {
            storage[size] = r;
            size++;
        }
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

    // Return index of resume if it exists in storage
    protected Integer getIndexByUuid(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

}
