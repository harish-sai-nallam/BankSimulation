package com.banksimulation.service.serviceimpl;

import com.banksimulation.config.JdbcConnectionSetup;
import com.banksimulation.exception.ServiceException;
import com.banksimulation.model.Account;
import com.banksimulation.repository.AccountRepo;
import com.banksimulation.service.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.banksimulation.exception.AccountNotFoundException;
import com.banksimulation.exception.DataAccessException;

import javax.ws.rs.NotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AccountServiceImpl implements AccountService {
    private static Logger logger = LogManager.getLogger(AccountServiceImpl.class);
    private AccountRepo repo;
    private Connection connection;

    public AccountServiceImpl(Connection connection) {
        try{
            this.connection= connection;
        }catch (Exception e){}
        repo = new AccountRepo(connection);
    }

    @Override
    public boolean add(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        try {
            boolean added = repo.addAccount(account);
            if (!added) {
                logger.warn("Failed to add account {}", account.getAccountNumber());
            }
            return added;
        } catch (DataAccessException e) {
            logger.error("Error adding account {}", account.getAccountNumber(), e);
            throw new ServiceException("Failed to add account", e);
        }
    }


    @Override
    public void update(String accountNumber, Account acc) {
        int accountId;
        try {
            accountId = repo.getAccountIdUsingNumber(accountNumber);
            if (accountId <= 0) {
                throw new AccountNotFoundException("No account found with account number: " + accountNumber);
            }
        } catch (SQLException e) {
            throw new ServiceException("Error retrieving account ID", e);
        }

        logger.info("Service.update called for id={}", accountId);
        acc.setAccountId(accountId);

        boolean updated = repo.updateAccount(acc);
        if (updated != true) {
            logger.warn("No rows updated for account id={}", accountId);
            throw new NotFoundException("Account update failed for id=" + accountId);
        }
        logger.info("Service.update result: Updated");
    }


    @Override
    public Account get(String accountNumber) throws ServiceException {
        int accountId;
        try {
            accountId = repo.getAccountIdUsingNumber(accountNumber);
            if(accountId==0){
                throw new AccountNotFoundException("Could not find the account with the given Account number"+accountNumber);
            }
        }catch (SQLException e) {
            throw new ServiceException("Error retrieving account ID", e);
        }
        return repo.getAccount(accountId);
    }

    @Override
    public void delete(String accountNumber) {
        if (accountNumber == null || accountNumber.isEmpty()) {
            throw new IllegalArgumentException("Account number must be provided");
        }
        try {
            int accountId = repo.getAccountIdUsingNumber(accountNumber);
            if (accountId <= 0) {
                throw new AccountNotFoundException("No account found with account number: " + accountNumber);
            }
            repo.deleteAccount(accountId);
            logger.info("Deleted account for account number {}", accountNumber);
        } catch (SQLException e) {
            logger.error("Error deleting account {}", accountNumber, e);
            throw new ServiceException("Failed to find account", e);
        }
    }
    public List<Account> getAccounts()  {
        List<Account> accounts = repo.getAllAccounts();
        logger.info("Retrieved {} accounts", accounts.size());
        return accounts;
    }

}



