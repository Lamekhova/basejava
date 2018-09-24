package com.urise.sql;

import com.urise.exception.ExistStorageExeption;
import com.urise.exception.StorageException;
import org.postgresql.util.PSQLException;

import java.sql.SQLException;

public class ExeptionUtil {

    private ExeptionUtil() {
    }

    public static StorageException convertExeption(SQLException exeption) {
        if (exeption instanceof PSQLException) {
            if (exeption.getSQLState().equals("23505")) {
                return new ExistStorageExeption(null);
            }
        }
        return new StorageException(exeption);
    }
}
