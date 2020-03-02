package com.stepanian.sandbox.pureJdbc;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;

import static com.stepanian.sandbox.pureJdbc.ConnectionManager.getConnection;

@Slf4j
public class PreparedStatementTest {

    private static String INSERT_QUERY = "INSERT INTO client (first_name, last_name, birthday) VALUES (?, ?, ?)";
    private static String SELECT_QUERY = "SELECT * FROM client WHERE id = ?";
    private static String UPDATE_QUERY = "UPDATE client SET last_name = ? WHERE id = ?";
    private static String UPDATE_SET_PHOTO = "UPDATE client SET photo = ? WHERE id = ?";
    private static String SELECT_MULTIPLE = "SELECT * FROM client WHERE first_name = ?";

    public static void main(String[] args) throws IOException, SQLException {
        save("John", "Doe", LocalDate.of(1986, 5, 31));
        System.out.println(findOne(1));
        update("Bob", 5);
        setPhoto(1, "chamomile.jpg");
        selectMultiple("Nick");
        System.out.println(getInsertedId("John", "Doe", LocalDate.of(1986, 5, 31)));
        transactionalMethod("David", "Pirogov", LocalDate.of(1980, 6, 12));
        batchMethod("John", "Doe", LocalDate.of(1986, 5, 31));

    }

    public static void save(String firstName, String lastName, LocalDate birthday) throws IOException, SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setObject(3, birthday, Types.DATE);
            preparedStatement.execute();
        }
    }

    public static String findOne(Integer id) throws IOException, SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {
            preparedStatement.setInt(1, id);
            return showResultSet(preparedStatement.executeQuery());
        }
    }

    public static void update(String lastName, Integer id) throws IOException, SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setString(1, lastName);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        }
    }

    public static void setPhoto(Integer id, String fileName) throws IOException, SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SET_PHOTO);
             FileInputStream fileInputStream = new FileInputStream(Paths.get("src/main/resources/sql/" + fileName).toFile())
        ) {
            preparedStatement.setBinaryStream(1, fileInputStream);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        }
    }

    public static void selectMultiple(String firstName) throws IOException, SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_MULTIPLE)) {
            preparedStatement.setString(1, firstName);

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

    public static String showResultSet(ResultSet result) throws SQLException {
        ResultSetMetaData metaData = result.getMetaData();
        int columnCount = metaData.getColumnCount();
        StringBuilder stringBuilder = new StringBuilder();
        while (result.next()) {
            stringBuilder.append("\n");
            for (int i = 1; i <= columnCount; i++) {
                if (i > 1) {
                    stringBuilder.append(",	");
                }
                stringBuilder.append(result.getString(i));
            }
        }
        return stringBuilder.toString();
    }
}
