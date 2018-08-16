package com.urise.storage;

import com.urise.exception.StorageException;
import com.urise.model.Resume;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {

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
        System.out.println("Storage was cleaned");
    }

    @Override
    public void doUpdate(Resume resume, Object searchKey) {
        storage[(Integer) searchKey] = resume;
        System.out.println("Resume " + resume.getUuid() + " has been updated");
    }

    @Override
    public void doSave(Resume resume, Object searchKey) {
        if (size >= storage.length) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        insertElement(resume, (Integer) searchKey);
        size++;
        System.out.println("Resume " + resume.getUuid() + " was added in storage");
    }

    @Override
    public void doDelete(Object searchKey) {
        shiftElement((Integer) searchKey);
        storage[size - 1] = null;
        size--;
        System.out.println("Resume " + searchKey.toString() + " has been deleted");
    }

    @Override
    public Resume doGet(Object searchKey) {
        return storage[(Integer) searchKey];
    }

    @Override
    public List<Resume> doGetAllSorted() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    @Override
    public boolean isExist(Object searchKey) {
        return (Integer) searchKey >= 0;
    }

}
