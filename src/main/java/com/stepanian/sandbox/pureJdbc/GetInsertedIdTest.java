package com.stepanian.sandbox.pureJdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;

import static com.stepanian.sandbox.pureJdbc.util.QueryStorage.INSERT_QUERY;
import static com.stepanian.sandbox.pureJdbc.util.ConnectionManager.getConnection;

public class GetInsertedIdTest {

    public static void main(String[] args) throws IOException, SQLException {
        System.out.println(getInsertedId("John", "Doe", LocalDate.of(1986, 5, 31)));
    }

    public static int getInsertedId(String firstName, String lastName, LocalDate birthday) throws IOException, SQLException {
        int key = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setObject(3, birthday, Types.DATE);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                key = resultSet.getInt(1);
            }
        }
        return key;
    }
}
