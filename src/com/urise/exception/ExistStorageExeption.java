package com.urise.exception;

public class ExistStorageExeption extends StorageException {

    public ExistStorageExeption(String uuid) {
        super("Resume " + uuid + " already exist in storage", uuid);
    }
}
