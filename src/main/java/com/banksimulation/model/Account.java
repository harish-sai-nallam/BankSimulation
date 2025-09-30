package com.banksimulation.model;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.*;
import java.time.LocalDate;

public class Account {



    private int accountId;

    @Positive(message = "Customer ID must be positive")
    private int customerId;

    @NotNull(message = "Account type is required")
    @Pattern(regexp = "SAVINGS|JOINT|BUSINESS", message = "Account type must be SAVINGS, JOINT or BUSINESS")
    private String accountType;

    @NotNull(message = "Bank name is required")
    @Size(min = 2, max = 50, message = "Bank name length must be between 2 and 50")
    private String bankName;

    @NotNull(message = "Branch is required")
    @Size(min = 2, max = 50, message = "Branch length must be between 2 and 50")
    private String branch;

    @DecimalMin(value = "0.0", inclusive = true, message = "Balance cannot be negative")
    private double balance;

    @Pattern(regexp = "ACTIVE|INACTIVE|SUSPENDED", message = "Status must be ACTIVE, INACTIVE or SUSPENDED")
    private String status;

    @PastOrPresent(message = "Created date must be past or present")
    private LocalDate createdAt;

    @PastOrPresent(message = "Modified date must be past or present")
    private LocalDate modifiedAt;

    @NotNull(message = "Account number is required")
    @Pattern(regexp = "^[0-9]{9,18}$", message = "Account number must be 9 to 18 digits")
    private String accountNumber;

    @NotNull(message = "IFSC code is required")
    @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$", message = "Invalid IFSC code format")
    private String ifscCode;

    @NotNull(message = "Name on account is required")
    @Size(min = 2, max = 50, message = "Name on account length must be 2 to 50")
    private String nameOnAccount;

    @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Invalid phone number")
    private String phoneLinkedWithBank;

    @DecimalMin(value = "0.0", inclusive = true, message = "Saving amount cannot be negative")
    private double savingAmount;

    public Account(){

    }

    public Account(double savingAmount, String phoneLinkedWithBank, String nameOnAccount, String ifscCode, String accountNumber, LocalDate modifiedAt, LocalDate createdAt, String status, double balance, String branch, String bankName, String accountType, int customerId, int accountId) {
        this.savingAmount = savingAmount;
        this.phoneLinkedWithBank = phoneLinkedWithBank;
        this.nameOnAccount = nameOnAccount;
        this.ifscCode = ifscCode;
        this.accountNumber = accountNumber;
        this.modifiedAt = modifiedAt;
        this.createdAt = createdAt;
        this.status = status;
        this.balance = balance;
        this.branch = branch;
        this.bankName = bankName;
        this.accountType = accountType;
        this.customerId = customerId;
        this.accountId = accountId;
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
