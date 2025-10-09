package com.banksimulation.repository;

import com.banksimulation.config.JdbcConnectionSetup;
import com.banksimulation.exception.AccountNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.banksimulation.model.Account;

public class AccountRepo {
    private Connection connection;

    public AccountRepo(){
        try {
            this.connection=JdbcConnectionSetup.getConnection();
        }catch (Exception e){}
    }
    public AccountRepo(Connection connection){this.connection=connection;}
    private static final Logger logger= LogManager.getLogger(AccountRepo.class);

    private void ensureConnection(){
        try{
            if(connection.isClosed()|| connection==null){
                connection=JdbcConnectionSetup.getConnection();
            }
        }catch (SQLException| ClassNotFoundException e){e.printStackTrace();}
    }

    public boolean addAccount(Account account) {
        ensureConnection();
        String insertQuery = "INSERT INTO account (customerId, accountType, bankName, branch, " +
                "balance, status, createdAt, modifiedAt, accountNumber, ifscCode, nameOnAccount, phoneLinkedWithBank, savingAmount) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        logger.info("Initiated the account insertion to the account table");
        try (
             PreparedStatement statement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, account.getCustomerId());
            statement.setString(2, account.getAccountType());
            statement.setString(3, account.getBankName());
            statement.setString(4, account.getBranch());
            statement.setDouble(5, account.getBalance());
            statement.setString(6, account.getStatus());

            if (account.getCreatedAt() != null) {
                statement.setDate(7, java.sql.Date.valueOf(account.getCreatedAt()));
            } else {
                statement.setDate(7, null);
            }

            if (account.getModifiedAt() != null) {
                statement.setDate(8, java.sql.Date.valueOf(account.getModifiedAt()));
            } else {
                statement.setDate(8, null);
            }

            statement.setString(9, account.getAccountNumber());
            statement.setString(10, account.getIfscCode());
            statement.setString(11, account.getNameOnAccount());
            statement.setString(12, account.getPhoneLinkedWithBank());
            statement.setDouble(13, account.getSavingAmount());

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        account.setAccountId(rs.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                logger.error("Duplicate account number error adding account", e);
                return false;
            }
            e.printStackTrace();

        } catch (Exception e) {
            logger.error("Unexpected error", e);
        }

        return false;
    }


    public boolean updateAccount(Account account) {
       ensureConnection();
        String updateSQL = "UPDATE account SET customerId = ?, accountType = ?, bankName = ?, branch = ?, balance = ?, "
                + "status = ?, createdAt = ?, modifiedAt = ?, accountNumber = ?, ifscCode = ?, nameOnAccount = ?, "
                + "phoneLinkedWithBank = ?, savingAmount = ? WHERE accountId = ?";
        try{
             PreparedStatement statement = connection.prepareStatement(updateSQL);

            statement.setInt(1, account.getCustomerId());
            statement.setString(2, account.getAccountType());
            statement.setString(3, account.getBankName());
            statement.setString(4, account.getBranch());
            statement.setDouble(5, account.getBalance());
            statement.setString(6, account.getStatus());
            statement.setDate(7, account.getCreatedAt() != null ? java.sql.Date.valueOf(account.getCreatedAt()) : null);
            statement.setDate(8, account.getModifiedAt() != null ? java.sql.Date.valueOf(account.getModifiedAt()) : null);
            statement.setString(9, account.getAccountNumber());
            statement.setString(10, account.getIfscCode());
            statement.setString(11, account.getNameOnAccount());
            statement.setString(12, account.getPhoneLinkedWithBank());
            statement.setDouble(13, account.getSavingAmount());
            statement.setInt(14, account.getAccountId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Account updated successfully.");
                return rowsUpdated>0;
            } else {
                System.out.println("No account found with accountId: " + account.getAccountId());
                return false;
            }
        } catch (SQLException  e) {
            e.printStackTrace();
            System.out.println("Error updating account.");
            return false;
        }
    }
    
    public  List<Account> getAllAccounts(){
        ensureConnection();
        List<Account> accounts = new ArrayList<>();
        try{

            String getAllAccounts = "SELECT * FROM ACCOUNT;";
            PreparedStatement statement = connection.prepareStatement(getAllAccounts);
            try (ResultSet set = statement.executeQuery()) {
                while (set.next()) {
                    Account account = new Account();
                    account.setAccountId(set.getInt("accountId"));
                    account.setCustomerId(set.getInt("customerId"));
                    account.setAccountType(set.getString("accountType"));
                    account.setBankName(set.getString("bankName"));
                    account.setBranch(set.getString("branch"));
                    account.setBalance(set.getDouble("balance"));
                    account.setStatus(set.getString("status"));

                    java.sql.Date createdAtDate = set.getDate("createdAt");
                    if (createdAtDate != null) {
                        account.setCreatedAt(createdAtDate.toLocalDate());
                    }

                    java.sql.Date modifiedAtDate = set.getDate("modifiedAt");
                    if (modifiedAtDate != null) {
                        account.setModifiedAt(modifiedAtDate.toLocalDate());
                    }

                    account.setAccountNumber(set.getString("accountNumber"));
                    account.setIfscCode(set.getString("ifscCode"));
                    account.setNameOnAccount(set.getString("nameOnAccount"));
                    account.setPhoneLinkedWithBank(set.getString("phoneLinkedWithBank"));
                    account.setSavingAmount(set.getDouble("savingAmount"));


                    accounts.add(account);
                }
            }
        } catch (SQLException s) {
            logger.error("Error while fetching accounts: ", s);
        } catch (Exception e) {
            logger.error("General error while fetching accounts: ", e);
        }
        return accounts;
    }


    public static void createAccountTable() {
        try  {

            Connection connection = JdbcConnectionSetup.getConnection();
            String createTable =
                    "CREATE TABLE IF NOT EXISTS Account ( accountId INT PRIMARY KEY AUTO_INCREMENT ," +
                            "    customerId INT, accountType VARCHAR(20), bankName VARCHAR(50)," +
                            "    branch VARCHAR(50), balance DECIMAL(15, 2), status VARCHAR(20), createdAt DATE," +
                            "    modifiedAt DATE, accountNumber VARCHAR(20) UNIQUE,  ifscCode VARCHAR(20), " +
                            "    nameOnAccount VARCHAR(50), phoneLinkedWithBank VARCHAR(15), savingAmount DECIMAL(15, 2)," +
                            "    CONSTRAINT fk_account_customer FOREIGN KEY (customerId) " +
                            "REFERENCES Customer(customerId) " +
                            ");";


            PreparedStatement statement = connection.prepareStatement(createTable);
            statement.executeUpdate();

            logger.info("Account table is successfully created ! ");
        }catch (Exception e){
            logger.error("Account table creation was not successful! ");
        }
    }
    public Account getAccount(int accountId) {
        try {
            String query = "SELECT * FROM account WHERE accountId = ?";
            ensureConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, accountId);

            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    Account account = new Account();
                    account.setAccountId(set.getInt("accountId"));
                    account.setCustomerId(set.getInt("customerId"));
                    account.setAccountType(set.getString("accountType"));
                    account.setBankName(set.getString("bankName"));
                    account.setBranch(set.getString("branch"));
                    account.setBalance(set.getDouble("balance"));
                    account.setStatus(set.getString("status"));

                    java.sql.Date createdAtDate = set.getDate("createdAt");
                    if (createdAtDate != null) {
                        account.setCreatedAt(createdAtDate.toLocalDate());
                    }
                    java.sql.Date modifiedAtDate = set.getDate("modifiedAt");
                    if (modifiedAtDate != null) {
                        account.setModifiedAt(modifiedAtDate.toLocalDate());
                    }

                    account.setAccountNumber(set.getString("accountNumber"));
                    account.setIfscCode(set.getString("ifscCode"));
                    account.setNameOnAccount(set.getString("nameOnAccount"));
                    account.setPhoneLinkedWithBank(set.getString("phoneLinkedWithBank"));
                    account.setSavingAmount(set.getDouble("savingAmount"));

                    return account;
                }
            }
        } catch (SQLException e) {
            logger.error("Cannot find account with ID: " + accountId, e);
        }
        return null;
    }
    public boolean deleteAccount(int accountId){
        ensureConnection();
        String query="DELETE FROM ACCOUNT WHERE ACCOUNTID=?";
        try(PreparedStatement statement=connection.prepareStatement(query)){
            statement.setInt(1,accountId);
            if(statement.executeUpdate()>0) return true;
            else return false;

        }catch (Exception e){
            logger.info("Could not find the account to delete the account");
            return false;
        }
    }
    public double getBalanceByAccountNumber(String accountNumber) throws SQLException {
        String sql = "SELECT balance FROM Account WHERE accountNumber = ?";
        ensureConnection();
        try (
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, accountNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("balance");
                } else {
                    throw new IllegalArgumentException("Account number " + accountNumber + " not found");
                }
            }
        } catch (SQLException e) {
            logger.error("Database error while finding balance for account: " + accountNumber, e);
            throw e;
        }
    }
    public int getAccountIdUsingNumber(String accountNumber) throws SQLException {
        String sql = "SELECT accountId FROM Account WHERE accountNumber = ?";
        ensureConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, accountNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("accountId");
                } else {
                    logger.error("Account number not found: " + accountNumber);
                    throw new AccountNotFoundException("Account number " + accountNumber + " not found");
                }
            }
        } catch (SQLException e) {
            logger.error("Database error while finding account ID for account number: " + accountNumber, e);
            throw e;
        }
    }
    public int getCustomerIdUsingNumber(String accountNumber) throws SQLException {
        String sql = "SELECT customerId FROM Account WHERE accountNumber = ?";
        ensureConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, accountNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("customerId");
                } else {
                    logger.error("Account number not found: " + accountNumber);
                    throw new IllegalArgumentException("Account number " + accountNumber + " not found");
                }
            }
        } catch (SQLException e) {
            logger.error("Database error while finding customer ID for account number: " + accountNumber, e);
            throw e;
        }
    }


}


