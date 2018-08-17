package com.urise.storage;

import com.urise.model.Resume;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {

    private Map<String, Resume> mapResumeStorage = new HashMap<>();

    @Override
    public int size() {
        return mapResumeStorage.size();
    }

    @Override
    public void clear() {
        mapResumeStorage.clear();
    }

    @Override
    public Resume getSearchKey(String uuid) {
        return mapResumeStorage.get(uuid);
    }

    @Override
    public void doUpdate(Resume resume, Object searchKeyResume) {
        mapResumeStorage.put(resume.getUuid(), resume);
    }

    @Override
    public void doSave(Resume resume, Object searchKeyResume) {
        mapResumeStorage.put(resume.getUuid(), resume);
    }

    @Override
    public void doDelete(Object searchKeyResume) {
        mapResumeStorage.remove(((Resume) searchKeyResume).getUuid());
    }

    @Override
    public Resume doGet(Object searchKeyResume) {
        return mapResumeStorage.get(((Resume) searchKeyResume).getUuid());
    }

    @Override
    public List<Resume> doGetAllSorted() {
        return new ArrayList<>(mapResumeStorage.values());
    }

    @Override
    public boolean isExist(Object searchKeyResume) {
        return mapResumeStorage.containsValue(searchKeyResume);
    }


}
