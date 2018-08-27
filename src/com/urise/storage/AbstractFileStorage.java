package com.urise.storage;

import com.urise.exception.StorageException;
import com.urise.model.Resume;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {

    private File directory;

    public AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory);
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
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO Exeption", file.getName(), e);
        }
    }

    @Override
    protected void doSave(Resume resume, File file) {
        try {
            file.createNewFile();
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO Exeption", file.getName(), e);
        }
    }

    protected abstract void doWrite(Resume resume, File file) throws IOException;

    protected abstract Resume doRead(File file) throws IOException;

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("Error delete file", file.getName());
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("IO Exeption", file.getName(), e);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        ArrayList<Resume> listResume = new ArrayList<>();
        File[] listFile = directory.listFiles();
        if (listFile == null) {
            throw new StorageException("Error size directory", directory.getName());
        }
        for (File element : listFile) {
                listResume.add(doGet(element));
        }
        return listResume;
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    public int size() {
        File[] listFile = directory.listFiles();
        if (listFile == null) {
            throw new StorageException("Error size directory", directory.getName());
        }
        return listFile.length;
    }

    @Override
    public void clear() {
        File[] listFile = directory.listFiles();
        if (listFile == null) {
            throw new StorageException("Error clear directory", directory.getName());
        }
        for (File element : listFile) {
            if (element.isFile()) {
                element.delete();
            }
        }
    }
}
