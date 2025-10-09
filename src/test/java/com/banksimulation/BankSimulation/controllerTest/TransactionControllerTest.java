package com.banksimulation.BankSimulation.controllerTest;

import com.banksimulation.controller.TransactionController;
import com.banksimulation.model.Transaction;
import org.junit.jupiter.api.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.core.Response;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class TransactionControllerTest {

    private Connection connection;
    private TransactionController controller;

    @BeforeEach
    void setup() throws Exception {
        String url = "jdbc:h2:mem:" + System.nanoTime() + ";DB_CLOSE_DELAY=-1";
        connection = DriverManager.getConnection(url, "sa", "");
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                CREATE TABLE Transactions (
                    transactionId INT AUTO_INCREMENT PRIMARY KEY,
                    senderAccountNumber VARCHAR(20),
                    receiverAccountNumber VARCHAR(20),
                    utrNumber VARCHAR(30),
                    transactionType VARCHAR(20),
                    modeOfTransaction VARCHAR(20),
                    amount DECIMAL(15,2),
                    transactionTime TIMESTAMP
                )
            """);
        }
        controller = new TransactionController("1234567890");
    }

    @AfterEach
    void teardown() throws Exception {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS Transactions");
        }
        connection.close();
    }

    private boolean validate(Transaction transaction) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);
        return violations.isEmpty();
    }

    private Transaction makeTransaction() {
        Transaction t = new Transaction();
        t.setSenderAccountNumber("1234567890");
        t.setReceiverAccountNumber("3344556677");
        t.setUtrNumber("UTR" + UUID.randomUUID().toString().replace("-", "").substring(0, 16).toUpperCase());
        t.setTransactionType("TRANSFER");
        t.setModeOfTransaction("UPI");
        t.setAmount(500.50);
        t.setTransactionTime(LocalDateTime.now());
        return t;
    }

    @Test
    void transferSuccess() {
        Response resp = controller.transfer(makeTransaction());
        assertEquals(201, resp.getStatus());
    }

    @Test
    void transferFailure_invalidAmount() {
        Transaction t = makeTransaction();
        t.setAmount(0.0);
        boolean valid = validate(t);
        assertFalse(valid);
    }

    @Test
    void transferFailure_invalidSender() {
        Transaction t = makeTransaction();
        t.setSenderAccountNumber("12");
        boolean valid = validate(t);
        assertFalse(valid);
    }

    @Test
    void getTransactionsSuccess() {
        controller.transfer(makeTransaction());
        Response resp = controller.getTransactions();
        assertEquals(200, resp.getStatus());
    }


    @Test
    void transferFailure_illegalTransactionType() {
        Transaction t = makeTransaction();
        t.setTransactionType("INVALIDTYPE");
        boolean valid = validate(t);
        assertFalse(valid);
    }

    @Test
    void transferFailure_illegalMode() {
        Transaction t = makeTransaction();
        t.setModeOfTransaction("XYZ");
        boolean valid = validate(t);
        assertFalse(valid);
    }

    @Test
    void transferFailure_missingUTR() {
        Transaction t = makeTransaction();
        t.setUtrNumber("UTRINVALID");
        boolean valid = validate(t);
        assertFalse(valid);
    }


    @Test
    void transferFailure_invalidReceiver() {
        Transaction t = makeTransaction();
        t.setReceiverAccountNumber("99"); // Too short
        boolean valid = validate(t);
        assertFalse(valid);
    }

    @Test
    void transferFailure_negativeAmount() {
        Transaction t = makeTransaction();
        t.setAmount(-100.00);
        boolean valid = validate(t);
        assertFalse(valid);
    }

    @Test
    void getTransactions_emptyDb() {
        Response resp = controller.getTransactions();
        assertEquals(200, resp.getStatus());
    }

}

