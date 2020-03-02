package com.stepanian.sandbox.pureJdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;

import static com.stepanian.sandbox.pureJdbc.ConnectionManager.getConnection;

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
             FileInputStream fileInputStream = new FileInputStream(Paths.get("src/main/resources/sql/" + fileName).toFile());
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
