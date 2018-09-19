package com.urise.storage;

import com.urise.exception.ExistStorageExeption;
import com.urise.exception.NotExistStorageExeption;
import com.urise.model.Resume;
import com.urise.sql.SqlHelper;
import com.urise.sql.SqlPerform;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {

    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public int size() {
        return sqlHelper.perform("SELECT count(*) FROM resume", new SqlPerform<Integer>() {
            @Override
            public Integer execute(PreparedStatement preparedStatement) throws SQLException {
                ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next() ? resultSet.getInt(1) : 0;
            }
        });
    }

    @Override
    public void clear() {
        sqlHelper.perform("DELETE FROM resume");
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.perform("SELECT * FROM resume ORDER BY full_name", new SqlPerform<List<Resume>>() {
            @Override
            public List<Resume> execute(PreparedStatement preparedStatement) throws SQLException {
                ResultSet resultSet = preparedStatement.executeQuery();
            List<Resume> listResume = new ArrayList();
            while (resultSet.next()) {
                listResume.add(new Resume(resultSet.getString("uuid"), resultSet.getString("full_name")));
            }
            return listResume;
            }
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.perform("INSERT INTO resume (uuid, full_name) VALUES (?, ?)", new SqlPerform<Object>() {
            @Override
            public Object execute(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, resume.getFullName());
                try {
                    preparedStatement.execute();
                } catch (SQLException e) {
                    if (e.getSQLState().equals("23505")) { // unique_violation
                        throw new ExistStorageExeption(resume.getUuid());
                    }
                }
                return null;
            }
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.perform("UPDATE resume SET full_name=? WHERE uuid = ?", new SqlPerform<Resume>() {
            @Override
            public Resume execute(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, resume.getFullName());
                preparedStatement.setString(2, resume.getUuid());
                preparedStatement.execute();
                if (preparedStatement.executeUpdate() == 0) {
                    throw new NotExistStorageExeption(resume.getUuid());
                }
                return null;
            }
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.perform("SELECT * FROM resume WHERE uuid = ?", new SqlPerform<Resume>() {
            @Override
            public Resume execute(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    throw new NotExistStorageExeption(uuid);
                }
                return new Resume(uuid, resultSet.getString("full_name"));
            }
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.perform("DELETE FROM resume WHERE uuid = ?", new SqlPerform<Object>() {
            @Override
            public Object execute(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, uuid);
                if (preparedStatement.executeUpdate() == 0) {
                    throw new NotExistStorageExeption(uuid);
                }
                return null;
            }
        });
    }
}
