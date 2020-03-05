package com.stepanian.sandbox.pureJdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.stepanian.sandbox.pureJdbc.util.ConnectionManager.getConnection;
import static com.stepanian.sandbox.pureJdbc.util.QueryStorage.UPDATE_SET_PHOTO;

public class SetBlobTest {

    public static void main(String[] args) throws IOException, SQLException {
        setPhoto(1, "chamomile.jpg");
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
}
