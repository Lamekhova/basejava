package com.urise.storage;

import com.urise.model.Resume;
import java.util.*;

public class MapUuidStorage extends AbstractStorage {

    private Map<String, Resume> mapUuidStorage = new HashMap<>();

    @Override
    public int size() {
        return mapUuidStorage.size();
    }

    @Override
    public void clear() {
        mapUuidStorage.clear();
    }

    @Override
    public Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    public void doUpdate(Resume resume, Object searchKeyUuid) {
        mapUuidStorage.put(String.valueOf(searchKeyUuid), resume);
    }

    @Override
    public void doSave(Resume resume, Object searchKeyUuid) {
        mapUuidStorage.put(String.valueOf(searchKeyUuid), resume);
    }

    @Override
    public void doDelete(Object searchKeyUuid) {
        mapUuidStorage.remove(searchKeyUuid);
    }

    @Override
    public Resume doGet(Object searchKeyUuid) {
        return mapUuidStorage.get(searchKeyUuid);
    }

    @Override
    public List<Resume> doGetAllSorted() {
        return new ArrayList<>(mapUuidStorage.values());
    }

    @Override
    public boolean isExist(Object searchKeyUuid) {
        return mapUuidStorage.containsKey(searchKeyUuid);
    }


}
