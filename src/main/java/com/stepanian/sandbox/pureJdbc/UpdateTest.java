package com.stepanian.sandbox.pureJdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.stepanian.sandbox.pureJdbc.util.ConnectionManager.getConnection;
import static com.stepanian.sandbox.pureJdbc.util.QueryStorage.UPDATE_QUERY;

public class UpdateTest {

    public static void main(String[] args) throws IOException, SQLException {
        update("Another_last_name", 5);
    }

    public static void update(String lastName, Integer id) throws IOException, SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setString(1, lastName);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        }
    }
}
