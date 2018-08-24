package com.urise.storage;

import com.urise.exception.ExistStorageExeption;
import com.urise.exception.NotExistStorageExeption;
import com.urise.model.Resume;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SearchKey> implements Storage {

    private static final Logger LOGGER = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract SearchKey getSearchKey(String uuid);

    protected abstract void doUpdate(Resume resume, SearchKey searchKey);

    protected abstract void doSave(Resume resume, SearchKey searchKey);

    protected abstract void doDelete(SearchKey searchKey);

    protected abstract Resume doGet(SearchKey searchKey);

    public abstract List<Resume> doGetAllSorted();

    protected abstract boolean isExist(SearchKey searchKey);

    public void update(Resume resume) {
        LOGGER.info("Update " + resume);
        SearchKey searchKey = getExistSearchKey(resume.getUuid());
        doUpdate(resume, searchKey);
    }

    public void save(Resume resume) {
        LOGGER.info("Save " + resume);
        SearchKey searchKey = getNotExistSearchKey(resume.getUuid());
        doSave(resume, searchKey);
    }

    @Override
    public void delete(String uuid) {
        LOGGER.info("Delete " + uuid);
        SearchKey searchKey = getExistSearchKey(uuid);
        doDelete(searchKey);
    }

    public Resume get(String uuid) {
        LOGGER.info("Get " + uuid);
        SearchKey searchKey = getExistSearchKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        LOGGER.info("getAllSorted");
        List<Resume> allResume = doGetAllSorted();
        Collections.sort(allResume);
        return allResume;
    }

    public SearchKey getExistSearchKey(String uuid) {
        SearchKey searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageExeption(uuid);
        }
        return searchKey;
    }

    public SearchKey getNotExistSearchKey(String uuid) {
        SearchKey searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageExeption(uuid);
        }
        return searchKey;
    }
}
