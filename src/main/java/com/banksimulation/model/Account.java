package com.banksimulation.model;

import java.time.LocalDate;

public class Account {

    private int accountId;
    private int customerId;
    private String accountType;
    private String bankName;
    private String branch;
    private double balance;
    private String status;
    private LocalDate createdAt;
    private LocalDate modifiedAt;
    private String accountNumber;
    private String ifscCode;
    private String nameOnAccount;
    private String phoneLinkedWithBank;
    private double savingAmount;

    public Account() {}

    public Account(int accountId, int customerId, String accountType, String bankName, String branch,
                   double balance, String status, LocalDate createdAt, LocalDate modifiedAt,
                   String accountNumber, String ifscCode, String nameOnAccount, String phoneLinkedWithBank,
                   double savingAmount) {
        this.accountId = accountId;
        this.customerId = customerId;
        this.accountType = accountType;
        this.bankName = bankName;
        this.branch = branch;
        this.balance = balance;
        this.status = status;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.accountNumber = accountNumber;
        this.ifscCode = ifscCode;
        this.nameOnAccount = nameOnAccount;
        this.phoneLinkedWithBank = phoneLinkedWithBank;
        this.savingAmount = savingAmount;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDate modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getNameOnAccount() {
        return nameOnAccount;
    }

    public void setNameOnAccount(String nameOnAccount) {
        this.nameOnAccount = nameOnAccount;
    }

    public String getPhoneLinkedWithBank() {
        return phoneLinkedWithBank;
    }

    public void setPhoneLinkedWithBank(String phoneLinkedWithBank) {
        this.phoneLinkedWithBank = phoneLinkedWithBank;
    }

    public double getSavingAmount() {
        return savingAmount;
    }

    public void setSavingAmount(double savingAmount) {
        this.savingAmount = savingAmount;
    }
}

