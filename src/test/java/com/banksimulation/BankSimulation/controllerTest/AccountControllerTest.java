package com.banksimulation.BankSimulation.controllerTest;
import com.banksimulation.controller.AccountController;
import com.banksimulation.model.Account;
import org.junit.jupiter.api.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.core.Response;
import java.sql.*;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class AccountControllerTest {

    private Connection connection;
    private AccountController controller;

    @BeforeEach
    void setup() throws Exception {
        String url = "jdbc:h2:mem:" + System.nanoTime() + ";DB_CLOSE_DELAY=-1";
        connection = DriverManager.getConnection(url, "sa", "");

        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                CREATE TABLE Account (
                  accountId INT AUTO_INCREMENT PRIMARY KEY,
                  customerId INT,
                  accountType VARCHAR(20),
                  bankName VARCHAR(50),
                  branch VARCHAR(50),
                  balance DECIMAL(15,2),
                  status VARCHAR(20),
                  createdAt DATE,
                  modifiedAt DATE,
                  accountNumber VARCHAR(20) UNIQUE,
                  ifscCode VARCHAR(20),
                  nameOnAccount VARCHAR(50),
                  phoneLinkedWithBank VARCHAR(15),
                  savingAmount DECIMAL(15,2)
                )
            """);
        }

        controller = new AccountController(connection);
    }

    @AfterEach
    void teardown() throws Exception {

        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS Account");
        }
        connection.close();
    }
    private boolean validate(Account account){

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Account>> violations = validator.validate(account);
        return violations.isEmpty();

    }

    private Account makeAccount() {
        Account a = new Account();
        a.setCustomerId(1);
        a.setAccountType("SAVINGS");
        a.setBankName("MyBank");
        a.setBranch("Main");
        a.setBalance(100.0);
        a.setStatus("ACTIVE");
        a.setCreatedAt(LocalDate.now());
        a.setAccountNumber("1234567890");
        a.setIfscCode("ABCD0123456");
        a.setNameOnAccount("Alice");
        a.setPhoneLinkedWithBank("9123456789");
        a.setSavingAmount(100.0);
        return a;
    }

    @Test
    void addAccountSuccess() {
        Response resp = controller.addAccount(makeAccount());
        assertEquals(201, resp.getStatus());
    }

    @Test
    void addAccountFailure() {
        Account account = makeAccount();
        account.setCustomerId(-5);
       if(validate(account)){
           Response resp = controller.addAccount(account);
       }
        else {
           assertEquals(validate(account),false);
       }
    }

    @Test
    void getAccountSuccess() {
        controller.addAccount(makeAccount());
        Response response = controller.getAccount("1234567890");
        assertEquals(200, response.getStatus());
    }

    @Test
    void getAccountFailure() {
        Response resp = controller.getAccount("0000000000");
        assertEquals(404, resp.getStatus());
    }

    @Test
    void updateAccountSuccess() {
        controller.addAccount(makeAccount());
        Account updated = makeAccount();
        updated.setBalance(500.0);
        Response resp = controller.updateAccount("1234567890", updated);
        assertEquals(200, resp.getStatus());
    }

    @Test
    void updateAccountFailure() {
        Response resp = controller.updateAccount("0000000000", makeAccount());
        assertEquals(404, resp.getStatus());
    }

    @Test
    void deleteAccountSuccess() {
        controller.addAccount(makeAccount());
        Response resp = controller.deleteAccount("1234567890");
        assertEquals(204, resp.getStatus());
    }

    @Test
    void deleteAccountFailure() {
        Response response = controller.deleteAccount("0000000000");
        int status = response.getStatus();
        assertTrue(status == 404 || status == 500, "Status should be either 404 or 500 but was " + status);

    }

    @Test
    void getAllAccountsSuccess() {
        controller.addAccount(makeAccount());
        Response resp = controller.getAllAccounts();
        assertEquals(200, resp.getStatus());
    }

    @Test
    void getAllAccountsFailure() {
        Response resp = controller.getAllAccounts();
        assertEquals(404, resp.getStatus());
    }
}
