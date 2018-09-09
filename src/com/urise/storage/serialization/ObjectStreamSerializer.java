package com.urise.storage.serialization;

import com.urise.exception.StorageException;
import com.urise.model.Resume;

import java.io.*;

/**
 * save and restore file in ObjectStreamFormat
 */
public class ObjectStreamSerializer implements Serializer {

    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(resume);
            objectOutputStream.flush();
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try(ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            return (Resume) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null);
        }
    }
}
