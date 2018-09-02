package com.urise.storage;

import com.urise.serialization.ObjectStreamSerializer;

public class ObjectStreamPathStorage extends AbstractPathStorage {

    public ObjectStreamPathStorage(String directory) {
        super(directory);
        this.serializer = new ObjectStreamSerializer();
    }

}
