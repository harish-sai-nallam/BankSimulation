package com.banksimulation.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;


public class JdbcConnectionSetup {
    private static final String username;
    private static final String password;
    private static final String url;
     static Connection connection;
     static {
         Properties properties = new Properties();
         try (InputStream input = JdbcConnectionSetup.class.getClassLoader().getResourceAsStream("application.properties")) {
             if (input != null) {
                 properties.load(input);
             }
         }
         catch (IOException e){e.printStackTrace();}
         url = properties.getProperty("url");
         username = properties.getProperty("username");
         password = properties.getProperty("password");
     }

    public static Connection getConnection () throws SQLException,ClassNotFoundException{
        if(connection==null || connection.isClosed()){
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection=DriverManager.getConnection(url, username, password);
                return connection;
            } catch (SQLException|ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
       return connection;
    }

}
