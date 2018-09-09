package com.urise.storage;

import com.urise.storage.serialization.XmlStreamSerializer;

public class XmlPathStorageTest extends AbstractStorageTest {

    public XmlPathStorageTest() {
        super(new PathStorage(FILE_STORAGE_DIR.getAbsolutePath(), new XmlStreamSerializer()));
    }

}