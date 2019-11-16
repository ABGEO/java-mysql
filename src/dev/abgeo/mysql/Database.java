package dev.abgeo.mysql;

import java.sql.*;

public class Database {
    private String databaseUrl;
    private Connection connection;
    private String user;
    private String password;

    public Database(
            String host,
            String user,
            String password,
            String database,
            String port
    ) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            databaseUrl = String.format("jdbc:mysql://%s:%s/%s", host, port, database);
            this.user = user;
            this.password = password;

            this.connection = connect();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Create connection to database.
     *
     * @return Connection.
     * @throws ClassNotFoundException ClassNotFoundException.
     * @throws SQLException           SQLException.
     */
    public Connection connect() {
        try {
            return DriverManager.getConnection(databaseUrl, user, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public void select(String table, String[] fields) {
        StringBuilder normalizedFields = new StringBuilder();
        for (String field: fields) {
            normalizedFields.append(field).append(", ");
        }
        normalizedFields = new StringBuilder(normalizedFields.substring(0, normalizedFields.length() - 2));

        String sql = String.format("SELECT %s FROM %s;", normalizedFields, table);

        Statement stmt = null;
        try {
            stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                for (String field: fields) {
                    System.out.print(rs.getString(field) + " ");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Close active connection;
     *
     * @throws SQLException SQLException.
     */
    public void close() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
