package com.banksimulation.model;
import javax.validation.constraints.*;
import java.time.LocalDate;

public class Transaction {


    private int transactionId;

    @NotNull(message = "Sender account number is required")
    @Pattern(regexp = "^[0-9]{9,18}$", message = "Sender account number must be 9-18 digits")
    private String senderAccountNumber;

    @NotNull(message = "Receiver account number is required")
    @Pattern(regexp = "^[0-9]{9,18}$", message = "Receiver account number must be 9-18 digits")
    private String receiverAccountNumber;

    @NotNull(message = "Transaction type is required")
    @Pattern(regexp = "DEPOSIT|WITHDRAW|TRANSFER", message = "Transaction type must be DEPOSIT, WITHDRAW, or TRANSFER")
    private String transactionType;

    @NotNull(message = "Mode of transaction is required")
    @Pattern(regexp = "CREDIT|DEBIT|UPI|QR_CODE", message = "Mode of transaction must be CREDIT, DEBIT, UPI, or QR_CODE")
    private String modeOfTransaction;

    @DecimalMin(value = "0.01", inclusive = true, message = "Amount must be at least 0.01")
    private double amount;


    @NotNull(message = "Transaction date is required")
    private LocalDate transactionDate;


    public Transaction() {}


    public Transaction(int transactionId, String senderAccountNumber, String receiverAccountNumber,
                       String transactionType, String modeOfTransaction, double amount, LocalDate transactionDate) {
        this.transactionId = transactionId;
        this.senderAccountNumber = senderAccountNumber;
        this.receiverAccountNumber = receiverAccountNumber;
        this.transactionType = transactionType;
        this.modeOfTransaction = modeOfTransaction;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }


    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getSenderAccountNumber() {
        return senderAccountNumber;
    }

    public void setSenderAccountNumber(String senderAccountNumber) {
        this.senderAccountNumber = senderAccountNumber;
    }

    public String getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    public void setReceiverAccountNumber(String receiverAccountNumber) {
        this.receiverAccountNumber = receiverAccountNumber;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getModeOfTransaction() {
        return modeOfTransaction;
    }

    public void setModeOfTransaction(String modeOfTransaction) {
        this.modeOfTransaction = modeOfTransaction;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
}

