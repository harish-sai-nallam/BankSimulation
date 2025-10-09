package com.banksimulation.BankSimulation.controllerTest;

import com.banksimulation.controller.CustomerController;
import com.banksimulation.model.Customer;
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
class CustomerControllerTest {

    private Connection connection;
    private CustomerController controller;

    @BeforeEach
    void setup() throws Exception {
        String url = "jdbc:h2:mem:" + System.nanoTime() + ";DB_CLOSE_DELAY=-1";
        connection = DriverManager.getConnection(url, "sa", "");
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                CREATE TABLE Customer (
                    customerId INT PRIMARY KEY AUTO_INCREMENT,
                    customerName VARCHAR(100),
                    aadharNumber VARCHAR(12),
                    permanentAddress VARCHAR(255),
                    state VARCHAR(50),
                    country VARCHAR(50),
                    city VARCHAR(50),
                    email VARCHAR(100),
                    phoneNumber VARCHAR(15),
                    maritalStatus VARCHAR(20),
                    dob DATE,
                    age INT,
                    createdOn DATE,
                    modifiedOn DATE,
                    gender CHAR(1),
                    fatherName VARCHAR(100),
                    motherName VARCHAR(100)
                )
            """);
        }
        controller = new CustomerController(connection);
    }

    @AfterEach
    void teardown() throws Exception {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS Customer");
        }
        connection.close();
    }

    private boolean validate(Customer customer) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        return violations.isEmpty();
    }

    private Customer makeCustomer() {
        Customer c = new Customer();
        c.setCustomerName("Harish Sai");
        c.setAadharNumber("123456789012");
        c.setPermanentAddress("123 Main Street Hyderabad");
        c.setState("Telangana");
        c.setCountry("India");
        c.setCity("Hyderabad");
        c.setEmail("harish@example.com");
        c.setPhoneNumber("9876543210");
        c.setMaritalStatus("SINGLE");
        c.setDob(LocalDate.of(2000, 1, 1));
        c.setAge(24);
        c.setCreatedOn(LocalDate.now());
        c.setGender("M");
        c.setFatherName("Subba");
        c.setMotherName("Lakshmi");
        return c;
    }

    @Test
    void addCustomerSuccess() {
        Response resp = controller.addCustomer(makeCustomer());
        assertEquals(201, resp.getStatus());
    }

    @Test
    void addCustomerFailure() {
        Customer c = makeCustomer();
        c.setEmail("invalidemail");
        boolean valid = validate(c);
        assertFalse(valid);
    }

    @Test
    void getCustomerSuccess() {
        Customer customer=makeCustomer();
        customer.setCustomerId(1);
        controller.addCustomer(customer);
        Response resp = controller.getCustomer(1);
        assertTrue(resp.getStatus() == 200 || resp.getStatus() == 201);
    }

    @Test
    void getCustomerFailure() {
        Response resp = controller.getCustomer(99);
        assertEquals(404, resp.getStatus());
    }

    @Test
    void updateCustomerFailure1() {
        Customer c = makeCustomer();
        Response resp = controller.updateCustomer(99, c);
        assertTrue(resp.getStatus() == 400 || resp.getStatus() == 404);
    }
    @Test
    void updateCustomerFailure2() {
        controller.addCustomer(makeCustomer());
        Customer customer = makeCustomer();
        customer.setDob(LocalDate.now());
        Response resp = controller.updateCustomer(1, customer);
        assertEquals(400, resp.getStatus());
    }

    @Test
    void deleteCustomerSuccess() {
        Customer customer=makeCustomer();
        customer.setCustomerId(1);
        controller.addCustomer(customer);
        Response resp = controller.deleteCustomer(1);
        assertTrue(resp.getStatus() == 204 || resp.getStatus() == 200);
    }

    @Test
    void deleteCustomerFailure() {
        Response resp = controller.deleteCustomer(999);
        assertTrue(resp.getStatus() == 404 || resp.getStatus() == 500);
    }

    @Test
    void getAllCustomersSuccess() {
        controller.addCustomer(makeCustomer());
        Response resp = controller.getAllCustomers();
        assertEquals(200, resp.getStatus());
    }

    @Test
    void getAllCustomersFailure() {
        Response resp = controller.getAllCustomers();
        assertTrue(resp.getStatus() == 200 || resp.getStatus() == 204);
    }
}

