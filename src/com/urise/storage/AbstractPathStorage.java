package com.urise.storage;

import com.urise.exception.StorageException;
import com.urise.model.Resume;
import com.urise.serialization.SerializaionStrategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {

    private Path directory;

    protected SerializaionStrategy<Resume, InputStream, OutputStream> serializer;

    public AbstractPathStorage(String dir) {
        Objects.requireNonNull(dir);
        directory = Paths.get(dir);
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + "is not directory");
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void doUpdate(Resume resume, Path path) {
        try {
            serializer.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Error updating path", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void doSave(Resume resume, Path path) {
        try {
            Files.createFile(path);
            doUpdate(resume, path);
        } catch (IOException e) {
            throw new StorageException("Error saving path", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Error deleting path", path.getFileName().toString(), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return serializer.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Error getting path", path.getFileName().toString(), e);
        }
    }

    @Override
    public List<Resume> doCopyAll() {
        List<Path> paths;
        try {
            paths = Files.list(directory).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Error size path", directory.getFileName().toString());
        }
        List<Resume> listResume = new ArrayList<>(paths.size());
        for (Path element : paths) {
            listResume.add(doGet(element));
        }
        return listResume;
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    public int size() {
        return doCopyAll().size();
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Error deleting path", null);
        }
    }
}
