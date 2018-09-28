package com.urise.storage;

import com.urise.exception.NotExistStorageExeption;
import com.urise.model.ContactType;
import com.urise.model.Resume;
import com.urise.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public int size() {
        return sqlHelper.perform("SELECT count(*) FROM resume", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery(); //ResultSet выполняет команду SELECT, которая возвращает данные в виде ResultSet
            return resultSet.next() ? resultSet.getInt(1) : 0;
        });
    }

    @Override
    public void clear() {
        sqlHelper.perform("DELETE FROM resume");
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.perform(
                "SELECT * FROM resume r" +
                        "     LEFT JOIN contact c2" +
                        "            ON r.uuid = c2.resume_uuid" +
                        "      ORDER BY full_name, uuid", preparedStatement -> {
                    ResultSet resultSet = preparedStatement.executeQuery();
                    Map<String, Resume> mapOfResume = new LinkedHashMap<>();
                    while (resultSet.next()) {
                        String uuid = resultSet.getString("uuid");
                        String fullName = resultSet.getString("full_name");
                        Resume resume = mapOfResume.get(uuid);
                        if (resume == null){
                            resume = new Resume(uuid, fullName);
                            mapOfResume.put(uuid, resume);
                        }
                            addContacts(resume, resultSet);
                    }
                    return new ArrayList<>(mapOfResume.values());
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
            insertContacts(resume, connection);
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalPerform(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE resume SET full_name=? WHERE uuid = ?")) {
                preparedStatement.setString(1, resume.getFullName());
                preparedStatement.setString(2, resume.getUuid());
                preparedStatement.execute();
                if (preparedStatement.executeUpdate() == 0) {
                    throw new NotExistStorageExeption(resume.getUuid());
                }
            }
            deleteContacts(resume);
            insertContacts(resume, connection);
            return null;
        });
    }


    @Override
    public Resume get(String uuid) {
        return sqlHelper.perform("" +
                        "SELECT * FROM resume r" +
                        "    LEFT JOIN contact c " +
                        "           ON r.uuid = c.resume_uuid" +
                        "        WHERE r.uuid = ?"
                , preparedStatement -> {
                    preparedStatement.setString(1, uuid);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (!resultSet.next()) {
                        throw new NotExistStorageExeption(uuid);
                    }
                    Resume resume = new Resume(uuid, resultSet.getString("full_name"));
                    do {
                        addContacts(resume, resultSet);
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

    private void deleteContacts(Resume resume) {
        sqlHelper.perform("DELETE FROM contact WHERE resume_uuid = ?", preparedStatement -> {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.execute();
            return null;
        });
    }

    private void insertContacts(Resume resume, Connection connection) throws SQLException {
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
    }

    private void addContacts(Resume resume, ResultSet resultSet) throws SQLException {
        ContactType contactType = ContactType.valueOf(resultSet.getString("type"));
        String value = resultSet.getString("value");
        resume.addContact(contactType, value);
    }
}
