package com.urise.storage;

import com.urise.serialization.ObjectStreamSerializer;

import java.io.File;

public class ObjectStreamStorage extends AbstractFileStorage {

    public ObjectStreamStorage(File directory) {
        super(directory);
        this.serializer = new ObjectStreamSerializer();
    }

}
