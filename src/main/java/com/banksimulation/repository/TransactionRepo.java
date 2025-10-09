package com.banksimulation.repository;

import com.banksimulation.config.JdbcConnectionSetup;
import com.banksimulation.model.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


    public class TransactionRepo {

        private Connection connection;
        private static final Logger logger = LogManager.getLogger(TransactionRepo.class);

        public TransactionRepo(Connection connection) {
            this.connection = connection;
        }
        public TransactionRepo(){
            try{
                this.connection=JdbcConnectionSetup.getConnection();
            } catch (Exception e) {}
        }

        public List<Transaction> getTransactionsByAccountNumber(String accountNumber) throws SQLException {
            try {
                if (connection == null || connection.isClosed()) {
                    connection = JdbcConnectionSetup.getConnection();
                }
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }

            String query = "SELECT accountId FROM Account WHERE accountNumber = ?";
            int accountId;
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, accountNumber);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        accountId = rs.getInt("accountId");
                    } else {
                        throw new IllegalArgumentException("Account number not found: " + accountNumber);
                    }
                }
            }

            String sql =
                    "SELECT t.transactionId, t.fromAccountId, t.toAccountId, t.transactionType, " +
                            "t.modeOfTransaction, t.amount, t.transactionDate, t.utrNumber, " +
                            "fromAcc.accountNumber AS fromAccountNumber, " +
                            "toAcc.accountNumber   AS toAccountNumber " +
                            "FROM Transactions t " +
                            "JOIN Account fromAcc ON t.fromAccountId = fromAcc.accountId " +
                            "JOIN Account toAcc   ON t.toAccountId   = toAcc.accountId " +
                            "WHERE t.fromAccountId = ? OR t.toAccountId = ? " +
                            "ORDER BY t.transactionDate DESC, t.transactionId DESC";

            List<Transaction> transactions = new ArrayList<>();
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, accountId);
                statement.setInt(2, accountId);
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        Transaction txn = new Transaction();
                        txn.setTransactionId(rs.getInt("transactionId"));
                        txn.setSenderAccountNumber(rs.getString("fromAccountNumber"));
                        txn.setReceiverAccountNumber(rs.getString("toAccountNumber"));
                        txn.setTransactionType(rs.getString("transactionType"));
                        txn.setModeOfTransaction(rs.getString("modeOfTransaction"));
                        txn.setAmount(rs.getDouble("amount"));
                        txn.setUtrNumber(rs.getString("utrNumber"));
                        Timestamp ts = rs.getTimestamp("transactionDate");
                        if (ts != null) {
                            txn.setTransactionTime(ts.toLocalDateTime());
                        }
                        transactions.add(txn);
                    }
                }
            }
            return transactions;
        }

        public void transfer(Transaction transaction) throws SQLException {
            try {
                if (connection == null || connection.isClosed()) {
                    connection = JdbcConnectionSetup.getConnection();
                }
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }

            String senderAccountNumber = transaction.getSenderAccountNumber();
            String receiverAccountNumber = transaction.getReceiverAccountNumber();
            double amount = transaction.getAmount();

            String senderSelectSql = "SELECT accountId, balance FROM Account WHERE accountNumber = ?";
            int senderAccountId;
            double senderBalance;

            try (PreparedStatement ps = connection.prepareStatement(senderSelectSql)) {
                ps.setString(1, senderAccountNumber);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        senderAccountId = rs.getInt("accountId");
                        senderBalance = rs.getDouble("balance");
                    } else {
                        throw new IllegalArgumentException("Sender account not found: " + senderAccountNumber);
                    }
                }
            }

            String utrNumber = generateUTR();
            transaction.setUtrNumber(utrNumber);

            String query = "SELECT accountId, balance FROM Account WHERE accountNumber = ?";
            int receiverAccountId;
            double receiverBalance;
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, receiverAccountNumber);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        receiverAccountId = rs.getInt("accountId");
                        receiverBalance = rs.getDouble("balance");
                    } else {
                        throw new IllegalArgumentException("Receiver account not found: " + receiverAccountNumber);
                    }
                }
            }

            if (senderBalance < amount) {
                throw new IllegalArgumentException("Insufficient funds. Current balance: " + senderBalance);
            }

            String update = "UPDATE Account SET balance = ? WHERE accountId = ?";
            String insert = "INSERT INTO Transactions (fromAccountId, toAccountId, transactionType, modeOfTransaction, amount, transactionDate, utrNumber) VALUES (?, ?, ?, ?, ?, ?, ?)";

            connection.setAutoCommit(false);
            try (PreparedStatement sender = connection.prepareStatement(update);
                 PreparedStatement receiver = connection.prepareStatement(update);
                 PreparedStatement psTransaction = connection.prepareStatement(insert)) {

                double newSenderBalance = senderBalance - amount;
                sender.setDouble(1, newSenderBalance);
                sender.setInt(2, senderAccountId);
                sender.executeUpdate();

                double newReceiverBalance = receiverBalance + amount;
                receiver.setDouble(1, newReceiverBalance);
                receiver.setInt(2, receiverAccountId);
                receiver.executeUpdate();

                psTransaction.setInt(1, senderAccountId);
                psTransaction.setInt(2, receiverAccountId);
                psTransaction.setString(3, transaction.getTransactionType());
                psTransaction.setString(4, transaction.getModeOfTransaction());
                psTransaction.setDouble(5, amount);
                psTransaction.setTimestamp(6, Timestamp.valueOf(transaction.getTransactionTime()));
                psTransaction.setString(7, transaction.getUtrNumber());
                psTransaction.executeUpdate();

                connection.commit();
                logger.info("Transfer successful: " + amount + " from accountId " + senderAccountId + " to accountId " + receiverAccountId + ", UTR=" + utrNumber);

            } catch (SQLException e) {
                connection.rollback();
                logger.error("Could not perform transfer, transaction rolled back", e);
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        }

        public static void createTransactionsTable() {
            String createSQL =
                    "CREATE TABLE IF NOT EXISTS Transactions ( " +
                            "transactionId INT PRIMARY KEY AUTO_INCREMENT, " +
                            "fromAccountId INT NOT NULL, " +
                            "toAccountId INT NOT NULL, " +
                            "transactionType VARCHAR(20) NOT NULL, " +
                            "modeOfTransaction VARCHAR(20) NOT NULL, " +
                            "amount DECIMAL(15, 2) NOT NULL, " +
                            "transactionDate DATETIME, " +
                            "utrNumber VARCHAR(19) NOT NULL, " +
                            "FOREIGN KEY (fromAccountId) REFERENCES Account(accountId), " +
                            "FOREIGN KEY (toAccountId) REFERENCES Account(accountId))";

            try (Connection connection = JdbcConnectionSetup.getConnection();
                 PreparedStatement statement = connection.prepareStatement(createSQL)) {

                statement.executeUpdate();
                logger.info("Successfully created Transactions table");

            } catch (SQLException | ClassNotFoundException e) {
                logger.error("Cannot create Transactions table: " + e.getMessage(), e);
            }
        }


        public static String generateUTR() {
            return "UTR" + UUID.randomUUID().toString().replace("-", "").substring(0, 16).toUpperCase();
        }
    }
