package com.urise.storage;

import com.urise.model.Resume;
import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

    private List<Resume> listStorage = new ArrayList<>();

    @Override
    public int size() {
        return listStorage.size();
    }

    @Override
    public void clear() {
        listStorage.clear();
    }

    @Override
    public Integer getSearchKey(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (listStorage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    public void doUpdate(Resume resume, Integer searchKey) {
        listStorage.set(searchKey, resume);
    }

    @Override
    public void doSave(Resume resume, Integer searchKey) {
        listStorage.add(resume);
    }

    public void doDelete(Integer searchKey) {
        listStorage.remove((int) searchKey);
    }

    @Override
    public Resume doGet(Integer searchKey) {
        return listStorage.get(searchKey);
    }

    @Override
    public List<Resume> doGetAllSorted() {
        return new ArrayList<>(listStorage);
    }

    @Override
    public boolean isExist(Integer searchKey) {
        return searchKey != null;
    }
}
