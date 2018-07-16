package com.urise.storage;

import com.urise.model.Resume;
import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume r) {
        super.save(r);
        int foundIndex = getIndexByUuid(r.getUuid());
        if (foundIndex > -1) {
            System.out.println("ERROR: Resume has already been added in storage");
        } else {
            //find proper position
            int positionToInsert = - (foundIndex + 1);
            //copy elements to +1 position
            if (positionToInsert < size) {
                System.arraycopy(storage, positionToInsert, storage,
                        positionToInsert + 1, storage.length - positionToInsert - 1);
            }
            //insert resume to proper position
            storage[positionToInsert] = r;
            size++;
        }
    }

    @Override
    public void delete(String uuid) {
        Integer indexResume = getIndexByUuid(uuid);
        if (indexResume >= 0) {
            System.arraycopy(storage, indexResume + 1, storage, indexResume, storage.length - indexResume - 1);
            size--;
            System.out.println("Resume " + uuid + " has been deleted");
        } else {
            System.out.println("ERROR: Resume does not exist in storage");
        }
    }

    @Override
    protected Integer getIndexByUuid(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
