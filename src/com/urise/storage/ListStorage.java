package com.urise.storage;

import com.urise.model.Resume;
import java.util.ArrayList;

public class ListStorage extends AbstractStorage{

    protected ArrayList<Resume> listStorage = new ArrayList();

    @Override
    public int size() {
        return listStorage.size();
    }

    @Override
    public void clear() {
        listStorage.clear();
    }

    @Override
    public Resume[] getAll() {
        return listStorage.toArray(new Resume[listStorage.size()]);
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
    public void doUpdate(Resume resume, Object searchKey) {
        listStorage.set((Integer) searchKey, resume);
    }

    @Override
    public void doSave(Resume resume, Object searchKey) {
        listStorage.add(resume);
    }

    public void doDelete(Object searchKey) {
        listStorage.remove((int)searchKey);
    }

    @Override
    public Resume doGet(Object searchKey) {
        return listStorage.get((Integer) searchKey);
    }

    @Override
    public boolean isExist(Object searchKey) {
        return searchKey != null;
    }
}
