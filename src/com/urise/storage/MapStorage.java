package com.urise.storage;

import com.urise.model.Resume;
import java.util.*;

public class MapStorage extends AbstractStorage {

    private Map<String, Resume> mapStorage = new HashMap<>();

    @Override
    public int size() {
        return mapStorage.size();
    }

    @Override
    public void clear() {
        mapStorage.clear();
    }

    @Override
    public Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    public void doUpdate(Resume resume, Object searchKey) {
        mapStorage.put(String.valueOf(searchKey), resume);
    }

    @Override
    public void doSave(Resume resume, Object searchKey) {
        mapStorage.put(String.valueOf(searchKey), resume);
    }

    public void doDelete(Object searchKey) {
        mapStorage.remove(searchKey);
    }

    @Override
    public Resume doGet(Object searchKey) {
        return mapStorage.get(searchKey);
    }

    @Override
    public List<Resume> getAll() {
        return new ArrayList<>(mapStorage.values());
    }

    @Override
    public boolean isExist(Object searchKey) {
        return mapStorage.containsKey(searchKey);
    }


}
