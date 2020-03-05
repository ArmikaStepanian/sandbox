package com.stepanian.sandbox.pureJdbc;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;

import static com.stepanian.sandbox.pureJdbc.util.QueryStorage.INSERT_QUERY;
import static com.stepanian.sandbox.pureJdbc.util.QueryStorage.UPDATE_QUERY;
import static com.stepanian.sandbox.pureJdbc.util.ConnectionManager.getConnection;

@Slf4j
public class TransactionTest {

    public static void main(String[] args) throws IOException, SQLException {
        transactionalMethod("David", "Pirogov", LocalDate.of(1980, 6, 12));
    }

    public static void transactionalMethod(String firstName, String lastName, LocalDate birthday) throws IOException, SQLException {
        int key = 0;
        try (Connection connection = getConnection();
             PreparedStatement insert = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement update = connection.prepareStatement(UPDATE_QUERY)) {
            try {
                connection.setAutoCommit(false);
                insert.setString(1, firstName);
                insert.setString(2, lastName);
                insert.setObject(3, birthday, Types.DATE);
                insert.execute();
                ResultSet resultSet = insert.getGeneratedKeys();
                if (resultSet.next()) {
                    key = resultSet.getInt(1);
                }
                update.setString(1, firstName);
                update.setInt(2, key);
                update.execute();
                connection.commit();
            } catch (SQLException e) {
                log.error("SQLException", e);
                connection.rollback();
            }
        }
    }
}