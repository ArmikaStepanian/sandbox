package com.stepanian.sandbox.pureJdbc;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

@Slf4j
public class TestDB {

    public static void main(String[] args) throws IOException {
        try {
            runTest();
        } catch (SQLException ex) {
            log.error("SQLException: ", ex);
        }
    }

    public static void runTest() throws SQLException, IOException {
        try (Connection connection = getConnection();
             Scanner in = new Scanner(Paths.get("src/main/resources/sql/client.sql").toFile());
             Statement statement = connection.createStatement();
        ) {
            while (true) {
                if (in.hasNextLine()) {
                    String line = in.nextLine().trim();
                    statement.execute(line);
                } else {
                    break;
                }
            }
        }
    }

    public static Connection getConnection() throws SQLException, IOException {
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get("src/main/resources/database.properties"))) {
            props.load(in);
        }
        String drivers = props.getProperty("jdbc.drivers");
        if (drivers != null) {
            System.setProperty("jdbc.drivers", drivers);
        }
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");
        return DriverManager.getConnection(url, username, password);
    }
}
