package com.urise.storage;

import com.urise.exception.ExistStorageExeption;
import com.urise.exception.NotExistStorageExeption;
import com.urise.model.Resume;
import com.urise.storage.Storage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {

    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        int beforeSave = storage.size();
        Resume resumeToAdd = new Resume(UUID.randomUUID().toString());
        storage.save(resumeToAdd);
        Assert.assertEquals(beforeSave + 1, storage.size());
        Assert.assertNotNull(storage.get(resumeToAdd.getUuid()));
    }

    @Test
    public void update() {
        int beforeSave = storage.size();
        Resume resumeForUpdate = new Resume(UUID_1);
        storage.update(resumeForUpdate);
        Assert.assertEquals(beforeSave, storage.size());
        Assert.assertEquals(0, (Comparable) resumeForUpdate.compareTo(storage.get(resumeForUpdate.getUuid())));
    }

    @Test
    public void get() {
        int beforeSave = storage.size();
        Resume resume = storage.get(UUID_1);
        Assert.assertEquals(beforeSave, storage.size());
        Assert.assertEquals(UUID_1, resume.getUuid());
    }

    @Test(expected = NotExistStorageExeption.class)
    public void getNotExist() {
        storage.get("uuid4");
    }

    @Test(expected = NotExistStorageExeption.class)
    public void delete() {
        int beforeSave = storage.size();
        storage.delete(UUID_1);
        Assert.assertEquals(beforeSave - 1, storage.size());
        storage.delete(UUID_1);
    }

    @Test
    public void getAll() {
        Resume[] allResume = storage.getAll();
        Assert.assertEquals(3, allResume.length);
        Set<String> set = new HashSet<>();
        for (Resume element : allResume) {
            set.add(element.getUuid());
        }
        Assert.assertEquals(3, set.size());
        Assert.assertTrue(set.containsAll(Set.of(UUID_1, UUID_2, UUID_3)));
    }
}