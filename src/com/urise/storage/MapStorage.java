package com.urise.storage;

import com.urise.model.Resume;
import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    protected Map<String, Resume> mapStorage = new HashMap();

    @Override
    public int size() {
        return mapStorage.size();
    }

    @Override
    public void clear() {
        mapStorage.clear();
    }

    @Override
    public Resume[] getAll() {
        return mapStorage.values().toArray(new Resume[0]);
    }

    @Override
    public Object getSearchKey(String uuid) {
        return uuid;
    }

    // ???
    @Override
    public void doUpdate(Resume resume, Object searchKey) {
        mapStorage.put(String.valueOf(searchKey), resume);
    }

    @Override
    public void doSave(Resume resume, Object searchKey) {
        mapStorage.put(String.valueOf(searchKey), resume);
    }

    public void doDelete(Object searchKey, String uuid) {
        mapStorage.remove(searchKey);
    }

    @Override
    public Resume doGet(Object searchKey) {
        return mapStorage.get(searchKey);
    }

    @Override
    public boolean isExist(Object searchKey) {
        return mapStorage.containsKey(searchKey);
    }


}
