package com.stepanian.sandbox.pureJdbc.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ResultSetHandler {

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
