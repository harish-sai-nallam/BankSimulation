package com.banksimulation.model;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Transaction {


    private int transactionId;

    @NotNull(message = "Sender account number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Sender account number must be 9-18 digits")
    private String senderAccountNumber;

    @NotNull(message = "Receiver account number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Receiver account number must be 9-18 digits")
    private String receiverAccountNumber;

   // @NotNull(message = "UTR number is required")
    @Pattern(regexp = "^UTR[A-Z0-9]{16}$",
            message = "UTR must start with 'UTR' followed by 16 uppercase letters or digits " )
    private String utrNumber;


    @NotNull(message = "Transaction type is required")
    @Pattern(regexp = "DEPOSIT|WITHDRAW|TRANSFER", message = "Transaction type must be DEPOSIT, WITHDRAW, or TRANSFER")
    private String transactionType;

    @NotNull(message = "Mode of transaction is required")
    @Pattern(regexp = "CREDIT|DEBIT|UPI", message = "Mode of transaction must be CREDIT, DEBIT, or UPI")
    private String modeOfTransaction;

    @DecimalMin(value = "0.01", inclusive = true, message = "Amount must be at least 0.01")
    private double amount;


    @NotNull(message = "Transaction date is required")
    private LocalDateTime transactionTime;


    public Transaction() {}


    public Transaction(int transactionId, String senderAccountNumber, String receiverAccountNumber,
                       String transactionType, String modeOfTransaction, double amount, LocalDateTime transactionDate) {
        this.transactionId = transactionId;
        this.senderAccountNumber = senderAccountNumber;
        this.receiverAccountNumber = receiverAccountNumber;
        this.transactionType = transactionType;
        this.modeOfTransaction = modeOfTransaction;
        this.amount = amount;
        this.transactionTime = transactionDate;
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

    public String getUtrNumber() {
        return utrNumber;
    }

    public void setUtrNumber( String utrNumber) {
        this.utrNumber = utrNumber;
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

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }
}

