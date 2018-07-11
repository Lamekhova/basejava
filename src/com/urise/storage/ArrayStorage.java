package com.urise.storage;

import com.urise.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[1000];
    private int size = 0;

    // Reset the array
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    // Add resume in first free cell if it not exists in storage
    public void save(Resume r) {
        if (getIndexOfResumeByUuid(r.getUuid()) == null && size < storage.length) {
            storage[size] = r;
            size++;
        } else {
            System.out.println("ERROR: Resume has already been added in storage or storage is full");
        }
    }

    // Update resume if it exists in storage
    public void update(Resume r) {
        Integer indexResume = getIndexOfResumeByUuid(r.getUuid());
        if (indexResume != null) {
            storage[indexResume] = r;
            System.out.println("Resume " + r.getUuid() + " has been updated");
        } else {
            System.out.println("ERROR: Resume does not exist in storage");
        }
    }

    // Return resume if it exist in storage
    public Resume get(String uuid) {
        Integer indexResume = getIndexOfResumeByUuid(uuid);
        if (indexResume != null) {
            return storage[indexResume];
        } else {
            System.out.println("ERROR: Resume does not exist in storage");
            return null;
        }
    }

    // Delete resume if it exist in storage
    public void delete(String uuid) {
        Integer indexResume = getIndexOfResumeByUuid(uuid);
        if (indexResume != null) {
            storage[indexResume] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("ERROR: Resume does not exist in storage");
        }
    }

    // Return index of resume if it exists in storage
    public Integer getIndexOfResumeByUuid(String uuid) {
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

    public int size() {
        return size;
    }
}
