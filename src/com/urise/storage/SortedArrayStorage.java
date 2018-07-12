package com.urise.storage;

import com.urise.model.Resume;
import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume r) {
        if (size >= storage.length) {
            System.out.println("ERROR: Storage is full");
        }

        if (getIndexByUuid(r.getUuid()) == -1) {
            System.out.println("ERROR: Resume has already been added in storage");
        } else {
            int indexForInsert = Arrays.binarySearch(storage, 0, size, r);
            System.arraycopy(storage, indexForInsert + 1, storage, indexForInsert, storage.length - indexForInsert - 1);
            storage[indexForInsert] = r;
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
