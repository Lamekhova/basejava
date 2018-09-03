package com.urise.storage;

import com.urise.serialization.JsonStreamSerializer;

public class JsonPathStorageTest extends AbstractStorageTest {

    public JsonPathStorageTest() {
        super(new PathStorage(FILE_STORAGE_DIR.getAbsolutePath(), new JsonStreamSerializer()));
    }

}