package dev.abgeo.mysql;

import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    private final static String configFilePath =
            System.getProperty("user.dir") + "/resources/database.json";

    public static void main(String[] args) {
        DatabaseConfig config = readConfig();

        Database database = new Database(
                config.getHost(),
                config.getUser(),
                config.getPassword(),
                config.getDatabase(),
                config.getPort()
        );
    }

    /**
     * Read database config from JSON file.
     *
     * @return DatabaseConfig object.
     */
    private static DatabaseConfig readConfig() {
        File configFile = new File(configFilePath);

        // Create config, if it not exists.
        if (!configFile.exists()) {
            writeConfig();
        }

        // Read config from JSON file.

        String configContent = "{}";
        try {
            configContent = new String(Files.readAllBytes(Paths.get(configFilePath)));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        JSONObject configJSON = new JSONObject(configContent);
        DatabaseConfig config = new DatabaseConfig();

        // If config not valid, recreate it.
        if (!validateConfig(configJSON)) {
            System.out.println("Invalid config file! Re-create it.\n");
            writeConfig();
            config = readConfig();
        }

        config.setHost(configJSON.get("host").toString())
                .setPort(configJSON.get("port").toString())
                .setUser(configJSON.get("user").toString())
                .setPassword(configJSON.get("password").toString())
                .setDatabase(configJSON.get("database").toString());

        return config;
    }

    /**
     * Validate given config.
     *
     * @param config Config file JSON Object.
     * @return Config is valid.
     */
    private static boolean validateConfig(JSONObject config) {
        return config.has("host")
                && config.has("port")
                && config.has("database")
                && config.has("user")
                && config.has("password");
    }

    /**
     * Write config to JSON file.
     */
    private static void writeConfig() {
        JSONObject configJSON = new JSONObject();
        Scanner scanner = new Scanner(System.in);

        // Input config.

        System.out.print("Enter database host [localhost]: ");
        String host = scanner.nextLine();
        if (host.equals("")) {
            host = "localhost";
        }

        System.out.print("Enter database port [3306]: ");
        String port = scanner.nextLine();
        if (port.equals("")) {
            port = "3306";
        }

        System.out.print("Enter database user [root]: ");
        String user = scanner.nextLine();
        if (user.equals("")) {
            user = "root";
        }

        System.out.print("Enter database password []: ");
        String password = scanner.nextLine();

        System.out.print("Enter database name []: ");
        String database = scanner.nextLine();
        while (database.equals("")) {
            System.out.print("Database name can't be empty! Enter it: ");
            database = scanner.nextLine();
        }

        // Create JSON structure.
        configJSON.put("host", host);
        configJSON.put("user", user);
        configJSON.put("password", password);
        configJSON.put("database", database);
        configJSON.put("port", port);

        // Write config to file.
        try {
            File file = new File(configFilePath);
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);
            writer.write(configJSON.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
}
