package com.banksimulation.config;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;


public class JdbcConnectionSetup {
    private static final String username="root";
    private static final String password="localhost@123";
    private static final String url="jdbc:mysql://localhost:3306/bank_simulation";
    private static final String dbName="bank_simulation";
     static Connection connection;



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
