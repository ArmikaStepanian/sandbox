package com.stepanian.sandbox.pureJdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.stepanian.sandbox.pureJdbc.util.ConnectionManager.getConnection;
import static com.stepanian.sandbox.pureJdbc.util.QueryStorage.SELECT_MULTIPLE;
import static com.stepanian.sandbox.pureJdbc.util.ResultSetHandler.showResultSet;

public class SelectMultipleTest {

    public static void main(String[] args) throws IOException, SQLException {
        selectMultiple();
    }

    public static void selectMultiple() throws IOException, SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_MULTIPLE)) {

            boolean isResult = preparedStatement.execute();
            boolean done = false;
            while (!done) {
                if (isResult) {
                    ResultSet resultSet = preparedStatement.getResultSet();
                    System.out.println(showResultSet(resultSet));
                } else {
                    int updateCount = preparedStatement.getUpdateCount();
                    if (updateCount < 0) {
                        done = true;
                    }
                }
                if (!done) {
                    isResult = preparedStatement.getMoreResults();
                }
            }
        }
    }
}
