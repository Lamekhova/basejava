package com.urise.storage;

import com.urise.exception.StorageException;
import com.urise.model.Resume;
import org.junit.Test;
import static org.junit.Assert.fail;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveWithOverflow() {
        try {
            for (int i = storage.size(); i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume(i + "", "NewName"));
            }
        } catch (StorageException e) {
            fail("ERROR: Failed to add Resume");
        }
        storage.save(RESUME_4);
    }
}