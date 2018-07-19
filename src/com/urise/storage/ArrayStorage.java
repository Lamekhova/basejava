package com.urise.storage;

import com.urise.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void insertElement(Resume resume, int indexResume) {
        storage[size] = resume;
    }

    @Override
    public void shiftElement(int indexResume) {
        storage[indexResume] = storage[size - 1];
    }
}
