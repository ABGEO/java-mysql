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
    public Connection connect() throws ClassNotFoundException, SQLException {
        return DriverManager.getConnection(databaseUrl, user, password);
    }

    /**
     * Close active connection;
     *
     * @throws SQLException SQLException.
     */
    public void close() throws SQLException {
        this.connection.close();
    }
}

//            Statement stmt = con.createStatement();
//            ResultSet rs = stmt.executeQuery("select * from user");
//            while (rs.next()) {
//                System.out.println(rs.getInt("id") + "  " + rs.getString("username"));
//            }
