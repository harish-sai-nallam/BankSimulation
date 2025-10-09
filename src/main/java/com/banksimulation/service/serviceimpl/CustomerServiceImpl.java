package com.banksimulation.service.serviceimpl;

import com.banksimulation.config.JdbcConnectionSetup;
import com.banksimulation.exception.ServiceException;
import com.banksimulation.model.Customer;
import com.banksimulation.repository.CustomerRepo;
import com.banksimulation.service.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    private static Logger logger= LogManager.getLogger(CustomerServiceImpl.class);
    private CustomerRepo repo;
    Connection connection;
    public CustomerServiceImpl(){
        try{
            connection=JdbcConnectionSetup.getConnection();
            repo=new CustomerRepo(connection);
        }catch (Exception e){}
    }
    public CustomerServiceImpl(Connection connection){
        this.connection=connection;
        repo=new CustomerRepo(connection);
    }


    @Override
    public int add(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (customer.getCustomerId() < 0) {
            throw new IllegalArgumentException("Customer ID must be positive");
        }
        return repo.addCustomer(customer);
    }

    @Override
    public Customer get(int customerId) {
        if (customerId <= 0) {
            throw new IllegalArgumentException("Customer ID must be positive");
        }
        return repo.getCustomer(customerId);
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = repo.getAllCustomers();
        if (customers == null) {
            logger.warn("No customers found");
            return Collections.emptyList();
        }
        return customers;
    }

    @Override
    public int update(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (customer.getCustomerId() <= 0) {
            throw new IllegalArgumentException("Customer ID must be positive");
        }
        return repo.updateCustomer(customer);
    }

    @Override
    public int delete(int customerId) {
        if (customerId <= 0) {
            throw new IllegalArgumentException("Customer ID must be positive");
        }
        return repo.deleteCustomer(customerId);
    }

}
