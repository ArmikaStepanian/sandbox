package com.stepanian.sandbox.pureJdbc;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;

import static com.stepanian.sandbox.pureJdbc.util.QueryStorage.INSERT_QUERY;
import static com.stepanian.sandbox.pureJdbc.util.ConnectionManager.getConnection;

@Slf4j
public class BatchTest {

    public static void main(String[] args) throws IOException, SQLException {
        batchMethod("John", "Doe", LocalDate.of(1986, 5, 31));
    }

    public static void batchMethod(String firstName, String lastName, LocalDate birthday) throws IOException, SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            try {
                connection.setAutoCommit(false);
                preparedStatement.setString(1, firstName + "_1");
                preparedStatement.setString(2, lastName);
                preparedStatement.setObject(3, birthday, Types.DATE);
                preparedStatement.addBatch();

                preparedStatement.setString(1, firstName + "_2");
                preparedStatement.setString(2, lastName);
                preparedStatement.setObject(3, birthday, Types.DATE);
                preparedStatement.addBatch();

                preparedStatement.setString(1, firstName + "_3");
                preparedStatement.setString(2, lastName);
                preparedStatement.setObject(3, birthday, Types.DATE);
                preparedStatement.addBatch();

                int[] rows = preparedStatement.executeBatch();
                connection.commit();
                System.out.println("Inserted rows - " + rows.length);
            } catch (SQLException e) {
                log.error("SQLException", e);
                connection.rollback();
            }
        }
    }
}
