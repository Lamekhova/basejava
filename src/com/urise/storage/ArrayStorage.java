package com.urise.storage;

import com.urise.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage{

    @Override
    public void save(Resume r) {
        super.save(r);
        if (getIndexByUuid(r.getUuid()) >= 0) {
            System.out.println("ERROR: Resume has already been added in storage");
        } else {
            storage[size] = r;
            size++;
        }
    }

    @Override
    public void delete(String uuid) {
        Integer indexResume = getIndexByUuid(uuid);
        if (indexResume >= 0) {
            storage[indexResume] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("ERROR: Resume does not exist in storage");
        }
    }

    // Return index of resume if it exists in storage
    @Override
    protected Integer getIndexByUuid(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
