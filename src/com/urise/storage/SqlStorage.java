package com.urise.storage;

import com.urise.exception.NotExistStorageExeption;
import com.urise.model.ContactType;
import com.urise.model.Resume;
import com.urise.sql.SqlHelper;
import com.urise.sql.SqlPerform;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public int size() {

        return sqlHelper.perform("SELECT count(*) FROM resume", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : 0;
        });
    }

    @Override
    public void clear() {
        sqlHelper.perform("DELETE FROM resume");
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.perform("SELECT * FROM resume ORDER BY full_name", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Resume> listResume = new ArrayList();
            while (resultSet.next()) {
                listResume.add(new Resume(resultSet.getString("uuid"), resultSet.getString("full_name")));
            }
            return listResume;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalPerform(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, resume.getFullName());
                preparedStatement.execute();
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)")) {
                for (Map.Entry<ContactType, String> entry : resume.getResumeContact().entrySet()) {
                    preparedStatement.setString(1, resume.getUuid());
                    preparedStatement.setString(2, entry.getKey().name());
                    preparedStatement.setString(3, entry.getValue());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.perform("UPDATE resume SET full_name=? WHERE uuid = ?", (SqlPerform<Resume>) preparedStatement -> {
            preparedStatement.setString(1, resume.getFullName());
            preparedStatement.setString(2, resume.getUuid());
            preparedStatement.execute();
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageExeption(resume.getUuid());
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.perform("" +
                        "SELECT * FROM resume r" +
                        "    LEFT JOIN contact c ON r.uuid = c.resume_uuid" +
                        "        WHERE r.uuid = ?"
                , preparedStatement -> {
                    preparedStatement.setString(1, uuid);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (!resultSet.next()) {
                        throw new NotExistStorageExeption(uuid);
                    }

                    Resume resume = new Resume(uuid, resultSet.getString("full_name"));
                    do {
                        String value = resultSet.getString("value");
                        ContactType contactType = ContactType.valueOf(resultSet.getString("type"));
                        resume.addContact(contactType, value);
                    } while (resultSet.next());
                    return resume;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.perform("DELETE FROM resume WHERE uuid = ?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageExeption(uuid);
            }
            return null;
        });
    }
}
