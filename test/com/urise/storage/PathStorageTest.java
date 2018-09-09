package com.urise.storage;

import com.urise.storage.serialization.ObjectStreamSerializer;

public class PathStorageTest extends AbstractStorageTest {

    public PathStorageTest() {
        super(new PathStorage(FILE_STORAGE_DIR.getAbsolutePath(), new ObjectStreamSerializer()));
    }

}