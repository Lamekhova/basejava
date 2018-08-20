package com.urise.storage;

import com.urise.model.Resume;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {

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
    public void doUpdate(Resume resume, Resume searchKeyResume) {
        mapResumeStorage.put(resume.getUuid(), resume);
    }

    @Override
    public void doSave(Resume resume, Resume searchKeyResume) {
        mapResumeStorage.put(resume.getUuid(), resume);
    }

    @Override
    public void doDelete(Resume searchKeyResume) {
        mapResumeStorage.remove((searchKeyResume).getUuid());
    }

    @Override
    public Resume doGet(Resume searchKeyResume) {
        return mapResumeStorage.get((searchKeyResume).getUuid());
    }

    @Override
    public List<Resume> doGetAllSorted() {
        return new ArrayList<>(mapResumeStorage.values());
    }

    @Override
    public boolean isExist(Resume searchKeyResume) {
        return mapResumeStorage.containsValue(searchKeyResume);
    }


}
