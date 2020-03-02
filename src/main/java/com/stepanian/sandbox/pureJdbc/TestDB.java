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

    public static void main(String[] args) throws IOException, SQLException {
        fillTable();
    }

    private static void fillTable() throws SQLException, IOException {
        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Enter file name without extension, for example: client. Or enter EXIT");
            if (!in.hasNextLine()) {
                return;
            }
            String line = in.nextLine().trim();
            if (line.equalsIgnoreCase("EXIT")) {
                return;
            }
            try (Connection connection = getConnection();
                 Scanner file = new Scanner(Paths.get("src/main/resources/sql/" + line + ".sql").toFile());
                 Statement statement = connection.createStatement()) {
                while (true) {
                    if (!file.hasNextLine()) {
                        return;
                    }
                    String query = file.nextLine().trim();
                    if (query.endsWith(";")) {
                        query = query.substring(0, query.length() - 1);
                    }
                    try {
                        statement.execute(query);
                    } catch (SQLException ex) {
                        log.error("SQLException: ", ex);
                    }
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
