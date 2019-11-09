package dev.abgeo.mysql;

public class DatabaseConfig {
    private String password;
    private String database;
    private String port;
    private String host;
    private String user;

    public String getPassword() {
        return password;
    }

    public DatabaseConfig setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getDatabase() {
        return database;
    }

    public DatabaseConfig setDatabase(String database) {
        this.database = database;
        return this;
    }

    public String getPort() {
        return port;
    }

    public DatabaseConfig setPort(String port) {
        this.port = port;
        return this;
    }

    public String getHost() {
        return host;
    }

    public DatabaseConfig setHost(String host) {
        this.host = host;
        return this;
    }

    public String getUser() {
        return user;
    }

    public DatabaseConfig setUser(String user) {
        this.user = user;
        return this;
    }
}
