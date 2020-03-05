package com.stepanian.sandbox.pureJdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.stepanian.sandbox.pureJdbc.util.ConnectionManager.getConnection;
import static com.stepanian.sandbox.pureJdbc.util.QueryStorage.SELECT_QUERY;
import static com.stepanian.sandbox.pureJdbc.util.ResultSetHandler.showResultSet;

public class SelectTest {

    public static void main(String[] args) throws IOException, SQLException {
        System.out.println(findOne(1));
    }

    public static String findOne(Integer id) throws IOException, SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {
            preparedStatement.setInt(1, id);
            return showResultSet(preparedStatement.executeQuery());
        }
    }
}
