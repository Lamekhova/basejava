package com.urise.storage;

import com.urise.exception.ExistStorageExeption;
import com.urise.exception.NotExistStorageExeption;
import com.urise.exception.StorageException;
import com.urise.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {

    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME_1 = new Resume(UUID_1);

    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME_2 = new Resume(UUID_2);

    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_3 = new Resume(UUID_3);

    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_4 = new Resume(UUID_4);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertSize(4);
        Assert.assertEquals(RESUME_4, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageExeption.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

    @Test(expected = StorageException.class)
    public void saveWithOverflow() {
        try {
            for (int i = storage.size(); i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume(i + ""));
            }
        } catch (StorageException e) {
            Assert.fail("ERROR: Failed to add Resume");
        }
        storage.save(RESUME_4);
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_1);
        storage.update(newResume);
        Assert.assertEquals(newResume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageExeption.class)
    public void updateNotExist() {
        storage.get(UUID_4);
    }

    @Test
    public void get() {
        Assert.assertEquals(UUID_1, storage.get(RESUME_1.getUuid()));
    }

    @Test(expected = NotExistStorageExeption.class)
    public void getNotExist() {
        storage.get(UUID_4);
    }

    @Test(expected = NotExistStorageExeption.class)
    public void delete() {
        storage.delete(UUID_1);
        assertSize(2);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageExeption.class)
    public void deleteNotExist() {
        storage.delete(UUID_4);
    }

    @Test
    public void getAll() {
        Resume[] allResume = {RESUME_1, RESUME_2, RESUME_3};
        Assert.assertArrayEquals(allResume, storage.getAll());
    }

    private void assertSize(int size) {
        Assert.assertEquals(size, storage.size());
    }
}