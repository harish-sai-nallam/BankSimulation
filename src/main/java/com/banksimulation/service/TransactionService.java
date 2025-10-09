package com.banksimulation.service;

import com.banksimulation.model.Transaction;

import java.util.List;

public interface TransactionService {
    void addTransaction(Transaction transaction);
    String getTransactions(String accountNumber);
}
