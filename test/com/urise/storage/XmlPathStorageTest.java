package com.urise.storage;

import com.urise.serialization.ObjectStreamSerializer;

public class XmlPathStorageTest extends AbstractStorageTest {

    public XmlPathStorageTest() {
        super(new PathStorage(PATH_STORAGE_DIR, new ObjectStreamSerializer()));
    }

}