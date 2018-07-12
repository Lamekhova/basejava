package com.urise.storage;

import com.urise.model.Resume;

/**
 * Basic interface for Storage
 */
public interface Storage {

    // Reset the array
    void clear();

    // Save resume in first free cell if it not exists in storage
    void save(Resume r);

    // Update resume if it exists in storage
    void update(Resume r);

    // Return resume if it exist in storage
    Resume get(String uuid);

    // Delete resume if it exist in storage
    void delete(String uuid);

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll();

    int size();
}
