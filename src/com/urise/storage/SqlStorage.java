package com.urise.storage;

import com.urise.exception.NotExistStorageExeption;
import com.urise.model.ContactType;
import com.urise.model.Resume;
import com.urise.model.Section;
import com.urise.model.SectionType;
import com.urise.sql.SqlHelper;
import com.urise.sql.SqlPerform;
import com.urise.utill.JsonParser;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException();
        }
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
        return sqlHelper.transactionalPerform(connection -> {
            Map<String, Resume> mapOfResume = new LinkedHashMap<>();
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from resume ORDER BY full_name, uuid")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String uuid = resultSet.getString("uuid");
                    String fullName = resultSet.getString("full_name");
                    mapOfResume.put(uuid, new Resume(uuid, fullName));
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from contact")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String uuid = resultSet.getString("resume_uuid");
                    Resume resume = mapOfResume.get(uuid);
                    addContacts(resume, resultSet);
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from section")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String uuid = resultSet.getString("resume_uuid");
                    Resume resume = mapOfResume.get(uuid);
                    addSections(resume, resultSet);
                }
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
            insertSections(resume, connection);
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
            deleteContacts(resume, connection);
            deleteSections(resume, connection);
            insertContacts(resume, connection);
            insertSections(resume, connection);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalPerform(connection -> {
            Resume resume;
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM resume WHERE uuid=?")) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    throw new NotExistStorageExeption(uuid);
                }
                resume = new Resume(uuid, resultSet.getString("full_name"));
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM contact WHERE resume_uuid=?")) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    addContacts(resume, resultSet);
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM section WHERE resume_uuid=?")) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    addSections(resume, resultSet);
                }
            }
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

    private void deleteContacts(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM contact WHERE resume_uuid = ?")) {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.execute();
        }
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

    private void deleteSections(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM section WHERE resume_uuid = ?")) {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.execute();
        }
    }

    private void insertSections(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO section(resume_uuid, type, content) VALUES (?, ?, ?)")) {
            for (Map.Entry<SectionType, Section> entry : resume.getResumeSection().entrySet()) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, entry.getKey().name());
                Section section = entry.getValue();
                preparedStatement.setString(3, JsonParser.write(section, Section.class));
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    private void addSections(Resume resume, ResultSet resultSet) throws SQLException {
        SectionType sectionType = SectionType.valueOf(resultSet.getString("type"));
        String content = resultSet.getString("content");
        resume.addSection(sectionType, JsonParser.read(content, Section.class));
    }
}
