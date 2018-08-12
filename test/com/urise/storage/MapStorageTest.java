package com.urise.storage;

import com.urise.model.Resume;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class MapStorageTest extends AbstractStorageTest{

    public MapStorageTest() {
        super(new MapStorage());
    }

    @Ignore
    @Test
    public void saveWithOverflow() {
    }

    @Test
    public void getAll() {
        Set<Resume> allResume = new HashSet<>(Arrays.asList(storage.getAll()));
        assertTrue(allResume.contains(RESUME_1));
        assertTrue(allResume.contains(RESUME_2));
        assertTrue(allResume.contains(RESUME_3));
        assertSize(3);
    }
}