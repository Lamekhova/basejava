package com.urise.storage;

import com.urise.storage.serialization.ObjectStreamSerializer;

public class FileStorageTest extends AbstractStorageTest {

    public FileStorageTest() {
        super(new FileStorage(FILE_STORAGE_DIR, new ObjectStreamSerializer()));
    }
}