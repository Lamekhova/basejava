package com.urise.storage;

import com.urise.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void clear() {

    }

    @Override
    public void save(Resume r) {

    }

    @Override
    public void update(Resume r) {

    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    protected Integer getIndexByUuid(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        Arrays.binarySearch(storage, 0, size, searchKey);
        return null;
    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }
}
