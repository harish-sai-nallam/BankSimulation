package com.banksimulation.repository;

import com.banksimulation.config.JdbcConnectionSetup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CustomerRepo {
    private static final Logger logger= LogManager.getLogger(CustomerRepo.class);
    public static void createTableCustomer(){
        try{
            Connection connection= JdbcConnectionSetup.getConnection();
            String createTable= "CREATE TABLE Customer (customerId INT PRIMARY KEY, password VARCHAR(255)," +
                    " customerName VARCHAR(50), aadharNumber INT, permanentAddress VARCHAR(255)," +
                    " state VARCHAR(50), country VARCHAR(50), city VARCHAR(50), email VARCHAR(100), " +
                    "phoneNumber INT, status VARCHAR(20), dob DATE, age INT, createdOn DATE," +
                    " modifiedOn DATE, gender CHAR(1),fatherName VARCHAR(50),motherName VARCHAR(50));";
            PreparedStatement statement=connection.prepareStatement(createTable);
            statement.executeUpdate();
            logger.info(" Customer table is successfully created ! ");
        }catch (Exception e){
            logger.error("Customer table creation was not successful! ");
        }
    }
}
