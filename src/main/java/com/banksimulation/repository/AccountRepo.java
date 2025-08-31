package com.banksimulation.repository;

import com.banksimulation.config.JdbcConnectionSetup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;


public class AccountRepo {
    private static final Logger logger= LogManager.getLogger(AccountRepo.class);
    public static void createAccountTable() {
        try  {

            Connection connection = JdbcConnectionSetup.getConnection();
            String createTable = "CREATE TABLE Account (accountId INT PRIMARY KEY, customerId INT, " +
                    "accountType VARCHAR(20), bankName VARCHAR(50), branch VARCHAR(50), balance DECIMAL(15, 2)," +
                    " status VARCHAR(20), createdAt DATE, modifiedAt DATE, accountNumber VARCHAR(20), " +
                    "ifscCode VARCHAR(20), nameOnAccount VARCHAR(50), " +
                    "phoneLinkedWithBank VARCHAR(15), savingAmount DECIMAL(15, 2));";


            PreparedStatement statement = connection.prepareStatement(createTable);
            statement.executeUpdate();

            logger.info("Account table is successfully created ! ");
        }catch (Exception e){
            logger.error("Account table creation was not successful! ");
        }
    }
}


