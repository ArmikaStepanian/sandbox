package com.stepanian.sandbox.pureJdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;

import static com.stepanian.sandbox.pureJdbc.util.QueryStorage.INSERT_QUERY;
import static com.stepanian.sandbox.pureJdbc.util.ConnectionManager.getConnection;

public class InsertTest {

    public static void main(String[] args) throws IOException, SQLException {
        insert("John", "Doe", LocalDate.of(1986, 5, 31));
    }

    public static void insert(String firstName, String lastName, LocalDate birthday) throws IOException, SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setObject(3, birthday, Types.DATE);
            preparedStatement.execute();
        }
    }
}
