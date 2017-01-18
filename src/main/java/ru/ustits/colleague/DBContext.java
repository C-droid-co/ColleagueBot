package ru.ustits.colleague;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author ustits
 */
@Accessors(fluent = true)
public class DBContext {

    private final static String URL = "jdbc:postgresql://localhost:5432/colleague";

    @Getter
    private static Connection connection;

    public static void init(final String userName, final String password) {
        try {
            connection = DriverManager.getConnection(URL, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
