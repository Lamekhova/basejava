package com.urise.exception;

public class NotExistStorageExeption extends StorageException {

    public NotExistStorageExeption(String uuid) {
        super("Resume " + uuid + " does not exist in storage", uuid);
    }
}
