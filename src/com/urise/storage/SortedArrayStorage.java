package com.urise.storage;

import com.urise.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    public void insertElement(Resume resume, int indexResume) {
        //find proper position
        int positionToInsert = -(indexResume + 1);
        //copy elements to +1 position
        if (positionToInsert < size) {
            System.arraycopy(storage, positionToInsert, storage,
                    positionToInsert + 1, size - positionToInsert);
        }
        //insert resume to proper position
        storage[positionToInsert] = resume;
    }

    @Override
    public void shiftElement(int indexResume) {
        System.arraycopy(storage, indexResume + 1, storage, indexResume,
                size - indexResume - 1);
    }
}
