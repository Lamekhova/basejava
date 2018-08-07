package com.urise.storage;

import com.urise.model.Resume;

/**
 * Basic interface for Storage
 */
public interface Storage {

    //Return amount of resume in storage
    int size();

    // Reset the storage
    void clear();

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll();

    // Save resume in first free cell in storage
    void save(Resume resume);

    // Update resume in storage
    void update(Resume resume);

    // Return resume from storage
    Resume get(String uuid);

    // Delete resume from storage
    void delete(String uuid);


}
