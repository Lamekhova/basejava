package com.urise.storage;

import com.urise.model.Resume;
import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume r) {
        if (size >= storage.length) {
            System.out.println("ERROR: Storage is full");
        }

        int foundIndex = Arrays.binarySearch(storage, 0, size, r);
        if (foundIndex > -1) {
            System.out.println("ERROR: Resume has already been added in storage");
        } else {
            //find proper position
            int positionToInsert = - (foundIndex + 1);
            //copy elements to +1 position
            if (positionToInsert < size) {
                System.arraycopy(storage, positionToInsert, storage,
                        positionToInsert + 1, storage.length - positionToInsert - 1);
            }
            //insert resume to proper position
            storage[positionToInsert] = r;
            size++;
        }
    }

    @Override
    protected Integer getIndexByUuid(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
