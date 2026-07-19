package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    static Properties properties = new Properties();

    public static Connection getConnection() throws SQLException {

        try(InputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String URL = properties.getProperty("db.url");
        String USERNAME = properties.getProperty("db.username");
        String PASSWORD = properties.getProperty("db.password");


        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }


}
