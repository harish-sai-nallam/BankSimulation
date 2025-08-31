package com.banksimulation.repository;

import com.banksimulation.config.JdbcConnectionSetup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class TranscationRepo {
    private static final Logger logger= LogManager.getLogger(TranscationRepo.class);
    public static void createTransactionTable(){
        try{
            Connection connection= JdbcConnectionSetup.getConnection();
            String createTable= "CREATE TABLE Transaction (transactionId INT PRIMARY KEY, " +
                    "transactionType VARCHAR(20), modeOfTransaction VARCHAR(20), amount DECIMAL(15, 2) );";
            PreparedStatement statement=connection.prepareStatement(createTable);
            statement.executeUpdate();
            logger.info("Transaction table is successfully created ! ");
        }catch (Exception e){
            logger.error("Transaction table creation was not successful! ");
        }
    }

}
