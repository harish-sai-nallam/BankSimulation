package com.banksimulation.service.serviceimpl;

import com.banksimulation.config.JdbcConnectionSetup;
import com.banksimulation.model.Transaction;
import com.banksimulation.repository.CustomerRepo;
import com.banksimulation.repository.TransactionRepo;
import com.banksimulation.service.TransactionService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {
    private static Logger logger= LogManager.getLogger(TransactionServiceImpl.class);
    private TransactionRepo repo;
    private Connection connection;
    private CustomerRepo customerRepo;
    public TransactionServiceImpl(){
        try{
            connection= JdbcConnectionSetup.getConnection();
            repo=new TransactionRepo(connection);
            customerRepo=new CustomerRepo(connection);
        }catch (Exception e){}
    }
    public TransactionServiceImpl(Connection connection){
            this.connection= connection;
            repo=new TransactionRepo(connection);
            customerRepo=new CustomerRepo(connection);
    }


    @Override
    public void addTransaction(Transaction transaction) {
        if(transaction.getReceiverAccountNumber()==transaction.getSenderAccountNumber()){
            logger.error("Sender and receiver should not be the same account ! ");
            return;
        }
        try {
            LocalDateTime today = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDate = today.format(formatter);
            repo.transfer(transaction);
            EmailServiceImpl email=new EmailServiceImpl();
            String from="banksimulationn@gmail.com";
            String to1=customerRepo.getCustomerEmail(transaction.getSenderAccountNumber());
            String subject1 = "₹" + transaction.getAmount() + " debited via " + transaction.getModeOfTransaction();
            String text1 = String.format("""
    Dear User,

    Transaction Details: 
    • Amount: ₹%.2f
    . Account Number: %s
    . UTR Number: %s
    • Type: %s
    • Transaction ID: %d
    • Date: %s
    • Status: COMPLETED

    Best regards,
    Bank Simulation Team
    """,
                    transaction.getAmount(),
                    transaction.getSenderAccountNumber(),
                    transaction.getUtrNumber(),
                    transaction.getModeOfTransaction(),
                    transaction.getTransactionId(),
                    formattedDate
            );

            boolean sent1= email.emailSender(from,to1,subject1,text1);
            if(sent1){
                logger.info("Sent the email to the  Money Sender");
            }else{
                logger.error("Email is not sent to Money Sender");
            }
            String to2= customerRepo.getCustomerEmail(transaction.getReceiverAccountNumber());
            String subject2 = "₹" + transaction.getAmount() + " credited via " + transaction.getModeOfTransaction();
            String text2 = String.format("""
    Dear User,

    Transaction Details: 
    • Amount: ₹%.2f
    . Account Number: %s
    . UTR Number: %s
    • Type: %s
    • Transaction ID: %d
    • Date: %s
    • Status: COMPLETED

    Best regards,
    Bank Simulation Team
    """,
                    transaction.getAmount(),
                    transaction.getReceiverAccountNumber(),
                    transaction.getUtrNumber(),
                    transaction.getModeOfTransaction(),
                    transaction.getTransactionId(),
                    formattedDate
            );
            boolean sent2=email.emailSender(from,to2,subject2,text2);
            if(sent2){
                logger.info("Sent the email to the Money Receiver");
            }else{
                logger.error("Email is not sent to the Receiver");
            }
        }catch (SQLException e){e.printStackTrace();}
    }

    @Override
    public String getTransactions(String accountNumber) {
        try {
           List<Transaction> transactions=repo.getTransactionsByAccountNumber(accountNumber);
            if (transactions.isEmpty()) {
                return "";
            }
                StringWriter sw = new StringWriter();
                try (CSVPrinter printer = new CSVPrinter(sw, CSVFormat.DEFAULT
                        .withHeader("Transaction ID", "Sender", "Receiver","utrNumber", "Type", "Mode", "Amount", "Time"))) {
                    for (Transaction t : transactions) {
                        printer.printRecord(
                                t.getTransactionId(),
                                t.getSenderAccountNumber(),
                                t.getReceiverAccountNumber(),
                                t.getUtrNumber(),
                                t.getTransactionType(),
                                t.getModeOfTransaction(),
                                t.getAmount(),
                                t.getTransactionTime()
                        );
                    }
                }
                return sw.toString();
        }catch (Exception e){
            return null;
        }
    }
}

