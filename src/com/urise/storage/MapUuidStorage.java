package com.urise.storage;

import com.urise.model.Resume;
import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {

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
    public String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    public void doUpdate(Resume resume, String searchKeyUuid) {
        mapUuidStorage.put(searchKeyUuid, resume);
    }

    @Override
    public void doSave(Resume resume, String searchKeyUuid) {
        mapUuidStorage.put(searchKeyUuid, resume);
    }

    @Override
    public void doDelete(String searchKeyUuid) {
        mapUuidStorage.remove(searchKeyUuid);
    }

    @Override
    public Resume doGet(String searchKeyUuid) {
        return mapUuidStorage.get(searchKeyUuid);
    }

    @Override
    public List<Resume> doGetAllSorted() {
        return new ArrayList<>(mapUuidStorage.values());
    }

    @Override
    public boolean isExist(String searchKeyUuid) {
        return mapUuidStorage.containsKey(searchKeyUuid);
    }
}
