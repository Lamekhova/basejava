package com.urise.storage;

import com.urise.serialization.ObjectStreamSerialization;

public class FileStorageTest extends AbstractStorageTest {

    public FileStorageTest() {
        super(new FileStorage(FILE_STORAGE_DIR, new ObjectStreamSerialization()));
    }
}