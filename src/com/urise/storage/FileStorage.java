package com.urise.storage;

import com.urise.exception.StorageException;
import com.urise.model.Resume;
import com.urise.serialization.SerializaionStrategy;
import com.urise.serialization.ObjectStreamSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {

    private File directory;

    private SerializaionStrategy serializer;

    public FileStorage(File directory) {
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
        List<Resume> list = new ArrayList<>();
        for (File file : getAllFiles(directory)) {
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
        return getAllFiles(directory).length;
    }

    @Override
    public void clear() {
        for (File element : getAllFiles(directory)) {
            doDelete(element);
        }
    }

    private File[] getAllFiles(File directory) {
        File[] listFile = directory.listFiles();
        if (listFile == null) {
            throw new StorageException("Error size directory", directory.getName());
        }
        return listFile;
    }
}
