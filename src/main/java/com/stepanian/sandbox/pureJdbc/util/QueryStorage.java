package com.stepanian.sandbox.pureJdbc.util;

public class QueryStorage {

    public static String INSERT_QUERY = "INSERT INTO client (first_name, last_name, birthday) VALUES (?, ?, ?)";
    public static String SELECT_QUERY = "SELECT * FROM client WHERE id = ?";
    public static String UPDATE_QUERY = "UPDATE client SET last_name = ? WHERE id = ?";
    public static String SELECT_MULTIPLE = "SELECT * FROM client";
    public static String UPDATE_SET_PHOTO = "UPDATE client SET photo = ? WHERE id = ?";
}
