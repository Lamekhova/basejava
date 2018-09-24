package com.urise.sql;

import com.urise.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    public final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void perform(String sqlQuery) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public <T> T perform(String sqlQuery, SqlPerform<T> sqlPerform) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            return sqlPerform.execute(preparedStatement);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public <T> T transactionalPerform(SqlTransaction sqlTransaction) {
        try (Connection connection = connectionFactory.getConnection()) {
             try {
                 connection.setAutoCommit(false);
                 T result = (T) sqlTransaction.execute(connection);
                 connection.commit();
                 return result;
             } catch (SQLException e) {
                 connection.rollback();
                 throw  ExeptionUtil.convertExeption(e);
             }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
