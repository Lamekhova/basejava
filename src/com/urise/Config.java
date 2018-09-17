package com.urise;

import com.urise.storage.SqlStorage;

import java.io.*;
import java.util.Properties;

public class Config {
    private final static Config INSTANCE = new Config();
    private final static String PATH_TO_PROPERTIES = "E:\\basejava\\config\\resumes_properties";

    private final File storageDir;
    private final SqlStorage storage;

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        File propertiesFile = new File(PATH_TO_PROPERTIES);
        try (InputStream inputStream = new FileInputStream(propertiesFile)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            storageDir = new File(properties.getProperty("storage.dir"));
            storage = new SqlStorage(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.user"),
                    properties.getProperty("db.password")
            );
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file" + propertiesFile.getAbsolutePath());
        }
    }

    public File getStorageDir() {
        return storageDir;
    }

    public SqlStorage getStorage() {
        return storage;
    }
}
