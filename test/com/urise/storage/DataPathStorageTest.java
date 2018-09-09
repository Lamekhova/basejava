package com.urise.storage;

import com.urise.storage.serialization.DataStreamSerializer;

public class DataPathStorageTest extends AbstractStorageTest {

    public DataPathStorageTest() {
        super(new PathStorage(FILE_STORAGE_DIR.getAbsolutePath(), new DataStreamSerializer()));
    }



}