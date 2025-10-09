package com.banksimulation.service;

import com.banksimulation.model.Account;
import com.google.protobuf.ServiceException;

import java.util.List;

public interface AccountService {
    boolean add(Account account) ;
    void update(String accountNumber,Account account) ;
    Account get(String accountNumber) ;
    List<Account> getAccounts();
    void delete(String accountNumber);

}
