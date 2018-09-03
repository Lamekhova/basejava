package com.urise.storage;

import com.urise.serialization.ObjectStreamSerialization;

public class PathStorageTest extends AbstractStorageTest {

    public PathStorageTest() {
        super(new PathStorage(PATH_STORAGE_DIR, new ObjectStreamSerialization()));
    }

}