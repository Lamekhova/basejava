package com.urise.storage;

import com.urise.exception.ExistStorageExeption;
import com.urise.exception.NotExistStorageExeption;
import com.urise.exception.StorageException;
import com.urise.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 1000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
        System.out.println("Storage was cleaned");
    }

    public void save(Resume resume) {
        int indexResume = getIndex(resume.getUuid());
        if (size >= storage.length) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        if (getIndex(resume.getUuid()) > -1) {
            throw new ExistStorageExeption(resume.getUuid());
        } else {
            insertElement(resume, indexResume);
            size++;
            System.out.println("Resume " + resume.getUuid() + " was added in storage");
        }
    }

    public void update(Resume resume) {
        int indexResume = getIndex(resume.getUuid());
        if (indexResume > -1) {
            storage[indexResume] = resume;
            System.out.println("Resume " + resume.getUuid() + " has been updated");
        } else {
            throw new NotExistStorageExeption(resume.getUuid());
        }
    }

    public Resume get(String uuid) {
        int indexResume = getIndex(uuid);
        if (indexResume > -1) {
            return storage[indexResume];
        }
        throw new NotExistStorageExeption(uuid);
    }

    @Override
    public void delete(String uuid) {
        int indexResume = getIndex(uuid);
        if (indexResume > -1) {
            shiftElement(indexResume);
            storage[size - 1] = null;
            size--;
            System.out.println("Resume " + uuid + " has been deleted");
        } else {
            throw new NotExistStorageExeption(uuid);
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    // Return index of resume if it exists in storage
    protected abstract int getIndex(String uuid);

    public abstract void insertElement(Resume r, int indexResume);

    public abstract void shiftElement(int indexResume);
}
