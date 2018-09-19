package com.urise.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlPerform<T> {
    T execute(PreparedStatement preparedStatement) throws SQLException;
}
