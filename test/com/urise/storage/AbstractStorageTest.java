package com.urise.storage;

import com.urise.exception.ExistStorageExeption;
import com.urise.exception.NotExistStorageExeption;
import com.urise.exception.StorageException;
import com.urise.model.Resume;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public abstract class AbstractStorageTest {

    protected Storage storage;

    protected static final String UUID_1 = "uuid1";
    protected static final Resume RESUME_1 = new Resume(UUID_1);

    protected static final String UUID_2 = "uuid2";
    protected static final Resume RESUME_2 = new Resume(UUID_2);

    protected static final String UUID_3 = "uuid3";
    protected static final Resume RESUME_3 = new Resume(UUID_3);

    protected static final String UUID_4 = "uuid4";
    protected static final Resume RESUME_4 = new Resume(UUID_4);

    public AbstractStorageTest(Storage storage) {
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
        assertEquals(RESUME_4, storage.get(UUID_4));
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
            fail("ERROR: Failed to add Resume");
        }
        storage.save(RESUME_4);
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_1);
        storage.update(newResume);
        assertEquals(newResume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageExeption.class)
    public void updateNotExist() {
        storage.get(UUID_4);
    }

    @Test
    public void get() {
        assertEquals(RESUME_1, storage.get(UUID_1));
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
        System.out.println(storage.getAll());
        assertArrayEquals(allResume, storage.getAll());
    }

    protected void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}