package com.banksimulation.service;

import com.banksimulation.model.Customer;

import java.util.List;

public interface CustomerService {
     int add(Customer customer);
     Customer get(int customerId);
     List<Customer> getAllCustomers();
     int update(Customer customer);
     int delete(int customerId);
}
