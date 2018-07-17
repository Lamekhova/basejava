package com.urise.storage;

import com.urise.model.Resume;
import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer getIndexByUuid(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    public void insertElement(Resume r) {
        int foundIndex = getIndexByUuid(r.getUuid());
        //find proper position
        int positionToInsert = - (foundIndex + 1);
        //copy elements to +1 position
        if (positionToInsert < size) {
            System.arraycopy(storage, positionToInsert, storage,
                    positionToInsert + 1, storage.length - positionToInsert - 1);
        }
        //insert resume to proper position
        storage[positionToInsert] = r;
    }

    @Override
    public void shiftElement(int indexResume) {
        System.arraycopy(storage, indexResume + 1, storage, indexResume, storage.length - indexResume - 1);
    }
}
