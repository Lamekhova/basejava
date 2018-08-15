package com.urise.storage;

import com.urise.model.Resume;
import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    public void insertElement(Resume resume, int indexResume) {
        //find proper position
        int positionToInsert = -(indexResume + 1);
        //copy elements to +1 position
        System.arraycopy(storage, positionToInsert, storage,
                positionToInsert + 1, size - positionToInsert);
        //insert resume to proper position
        storage[positionToInsert] = resume;
    }

    @Override
    public void shiftElement(int indexResume) {
        System.arraycopy(storage, indexResume + 1, storage, indexResume,
                size - indexResume - 1);
    }

    @Override
    public Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "NewName");
        return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR);
    }

}
