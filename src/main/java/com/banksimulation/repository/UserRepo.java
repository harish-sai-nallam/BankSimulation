package com.banksimulation.repository;

import com.banksimulation.entity.User;
import com.banksimulation.config.JdbcConnectionSetup;
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserRepo {
    private static final Logger logger=LogManager.getLogger(UserRepo.class);
    public boolean insertUser(User user){
        try{
            Connection connection= JdbcConnectionSetup.getConnection();
            logger.info("JDBC Connection is successful");
            String insert="INSERT INTO USERS(name ,email ,age , gender ,address ) VALUES (?,?,?,?,?)";
            PreparedStatement statement=connection.prepareStatement(insert);
            statement.setString(1,user.getName());
            statement.setString(2,user.getEmail());
            statement.setInt(3, user.getAge());
            statement.setString(4, user.getGender());
            statement.setString(5,user.getAddress());
            int status=statement.executeUpdate();
            return status>0;
        }
        catch (Exception r){
            r.printStackTrace();
            return false;
        }

    }
}