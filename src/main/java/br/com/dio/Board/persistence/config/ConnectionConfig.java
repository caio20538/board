package br.com.dio.Board.persistence.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionConfig {
    private ConnectionConfig() {
    }

    public static Connection getConnection() throws SQLException {
        var connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/board", "board", "board");
        connection.setAutoCommit(false);
        return connection;
    }

}
