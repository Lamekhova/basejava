package com.urise.storage;

import com.urise.exception.StorageException;
import com.urise.model.Resume;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    protected abstract void insertElement(Resume resume, int indexResume);

    protected abstract void shiftElement(int indexResume);

    @Override
    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void doUpdate(Resume resume, Integer searchKey) {
        storage[searchKey] = resume;
    }

    @Override
    public void doSave(Resume resume, Integer searchKey) {
        if (size >= storage.length) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        insertElement(resume, searchKey);
        size++;
    }

    @Override
    public void doDelete(Integer searchKey) {
        shiftElement(searchKey);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public Resume doGet(Integer searchKey) {
        return storage[searchKey];
    }

    @Override
    public List<Resume> doGetAllSorted() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    @Override
    public boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

}
