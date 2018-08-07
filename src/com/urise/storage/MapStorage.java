package com.urise.storage;

import com.urise.model.Resume;
import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    protected HashMap<String, Resume> mapStorage = new HashMap();

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
        return (Resume[]) mapStorage.values().toArray();
    }

    @Override
    public Object getSearchKey(String uuid) {
        for (Map.Entry<String, Resume> pair : mapStorage.entrySet()) {
            if (pair.getValue().getUuid().equals(uuid)) {
                return pair.getKey();
            }
        }
        return null;
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

    public void doDelete(Object searchKey) {
        mapStorage.remove(searchKey);
    }

    @Override
    public Resume doGet(Object searchKey) {
        return mapStorage.get(searchKey);
    }

    @Override
    public boolean isExist(Object searchKey) {
        return searchKey != null;
    }


}
