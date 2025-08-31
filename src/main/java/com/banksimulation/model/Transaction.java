package com.banksimulation.model;

public class Transaction {

    public enum TransactionType {
        DEPOSIT, WITHDRAW
    }

    public enum ModeOfTransaction {
        CREDIT, DEBIT, UPI, QR_CODE
    }

    private int transactionId;
    private TransactionType transactionType;
    private ModeOfTransaction modeOfTransaction;
    private double amount;


    public Transaction(int transactionId, TransactionType transactionType, ModeOfTransaction modeOfTransaction, double amount) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.modeOfTransaction = modeOfTransaction;
        this.amount = amount;
    }


    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public ModeOfTransaction getModeOfTransaction() {
        return modeOfTransaction;
    }

    public void setModeOfTransaction(ModeOfTransaction modeOfTransaction) {
        this.modeOfTransaction = modeOfTransaction;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
