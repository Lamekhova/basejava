package com.urise.storage;

import com.urise.exception.StorageException;
import com.urise.model.Resume;
import com.urise.serialization.SerializaionStrategy;
import com.urise.serialization.ObjectStreamSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {

    private File directory;

    protected SerializaionStrategy<Resume, InputStream, OutputStream> serializer;

    public AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory);
        this.serializer = new ObjectStreamSerializer();
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doUpdate(Resume resume, File file) {
        try {
            serializer.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO Exeption", file.getName(), e);
        }
    }

    @Override
    protected void doSave(Resume resume, File file) {
        try {
            file.createNewFile();
            doUpdate(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO Exeption", file.getName(), e);
        }
    }

//    protected abstract void doWrite(Resume resume, OutputStream outputStream) throws IOException;
//
//    protected abstract Resume doRead(InputStream inputStream) throws IOException;

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("Error delete file", file.getName());
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return serializer.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO Exeption", file.getName(), e);
        }
    }

    @Override
    public List<Resume> doCopyAll() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory read error", null);
        }
        List<Resume> list = new ArrayList<>(files.length);
        for (File file : files) {
            list.add(doGet(file));
        }
        return list;
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    public int size() {
        return getAllSorted().size();
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        for (File element : files) {
            if (element.isFile()) {
                doDelete(element);
            }
        }
    }
}
