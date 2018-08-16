package com.urise.storage;

import com.urise.model.Resume;
import org.junit.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.assertTrue;

public class MapStorageTest extends AbstractStorageTest{

    public MapStorageTest() {
        super(new MapStorage());
    }

    @Test
    public void getAllSorted() {
        Set<Resume> allResume = new HashSet<>(storage.getAllSorted());
        assertTrue(allResume.contains(RESUME_1));
        assertTrue(allResume.contains(RESUME_2));
        assertTrue(allResume.contains(RESUME_3));
        assertSize(3);
    }
}