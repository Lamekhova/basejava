package com.urise.storage;

import com.urise.Config;

public class SqlStorageTest extends AbstractStorageTest {

    public SqlStorageTest() {
        super(Config.get().getStorage());
    }
}